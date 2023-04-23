package com.example.microiam.user.execution;

import com.example.microiam.keycloak.RealmProperties;
import com.example.microiam.user.UserEntity;
import com.example.microiam.user.UserRepository;
import java.time.Clock;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class UserCreateExecutionService
    implements UserCreateExecution, InitializingBean, DisposableBean {

  private static final long SCHEDULER_INITIAL_DELAY_SECONDS = 10;
  private static final long SCHEDULER_DELAY_SECONDS = 60;
  private static final TimeUnit SCHEDULER_UNIT = TimeUnit.SECONDS;
  private static final long LOCK_PERIOD_MILLI = 2 * 60 * 1000;

  private static final Logger log = LoggerFactory.getLogger(UserCreateExecutionService.class);

  private final UserRepository userRepository;

  private final Clock clock;
  private final Keycloak keycloak;
  private final String realm;

  private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  public UserCreateExecutionService(
      UserRepository userRepository,
      Clock clock,
      Keycloak keycloak,
      RealmProperties realmProperties) {
    this.userRepository = userRepository;
    this.clock = clock;
    this.keycloak = keycloak;
    this.realm = realmProperties.getName();
  }

  @Override
  public void afterPropertiesSet() {
    executor.scheduleWithFixedDelay(
        this::execute, SCHEDULER_INITIAL_DELAY_SECONDS, SCHEDULER_DELAY_SECONDS, SCHEDULER_UNIT);
    log.info(
        "Scheduled execution of {} (initialDelay={}, delay={}, unit={})",
        UserCreateExecutionService.class.getSimpleName(),
        SCHEDULER_INITIAL_DELAY_SECONDS,
        SCHEDULER_DELAY_SECONDS,
        SCHEDULER_UNIT);
  }

  @Override
  public void destroy() throws Exception {
    executor.shutdown();
    while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
      log.info(
          "Awaiting termination of {} scheduler", UserCreateExecutionService.class.getSimpleName());
    }
  }

  @Override
  public void triggerUserCreation() {
    executor.execute(this::execute);
  }

  private void execute() {
    log.debug("Triggered execution of {}", UserCreateExecutionService.class.getSimpleName());
    try {
      executeInternal();
    } catch (Exception e) {
      log.error("Failed to create Keycloak user account on unexpected exception", e);
    }
  }

  private void executeInternal() {
    long creationLock = clock.instant().toEpochMilli();
    Optional<UserEntity> entity = findIdleEntity(creationLock);

    while (entity.isPresent()) {
      UserEntity user = entity.get();
      log.debug(
          "Executing user creation in Keycloak (realm={}, username={}, email={})",
          realm,
          user.username(),
          user.email());

      Optional<UserEntity> lock = acquireLock(user, creationLock);
      if (lock.isEmpty()) {
        continue;
      }
      user = lock.get();

      RealmResource realm = getKeycloakRealm();

      if (checkMatchingByUsername(realm, user) || checkMatchingByEmail(realm, user)) {
        log.error(
            "Skipping creation of Keycloak user account due to currently existing account and mismatched data (realm={}, username={}, email={})",
            realm,
            user.username(),
            user.email());
        continue;
      }

      UserRepresentation kcUser = createUserRepresentation(user);

      try (Response response = realm.users().create(kcUser)) {
        if (response.getStatus() / 100 == 2) {
          log.info(
              "Successfully created Keycloak user account (username={}, email={})",
              user.username(),
              user.email());

          String keycloakId = extractKeycloakId(response);
          user = setKeycloakIdAndCreatedStatus(user, keycloakId);
          user = userRepository.save(user);
        } else {
          // TODO: handle situation where user account exists
          log.error(
              "Failed to create Keycloak user account (username={}, email={}, status={})",
              user.username(),
              user.email(),
              response.getStatus());
        }
      }

      creationLock = clock.instant().toEpochMilli();
      entity = findNextIdleEntity(user, creationLock);
    }
  }

  private Optional<UserEntity> findIdleEntity(long creationLock) {
    return userRepository.findByCreationLockIdle(
        CreationStatus.PENDING, creationLock - LOCK_PERIOD_MILLI);
  }

  private RealmResource getKeycloakRealm() {
    return keycloak.realm(realm);
  }

  private UserEntity setCreationLock(UserEntity user, long creationLock) {
    return new UserEntity(
        user.id(),
        user.uuid(),
        user.username(),
        user.email(),
        user.createStatus(),
        user.updateStatus(),
        user.update(),
        creationLock,
        user.keycloakId(),
        user.version());
  }

  private Optional<UserEntity> acquireLock(UserEntity user, long creationLock) {
    user = setCreationLock(user, creationLock);
    try {
      user = userRepository.save(user);
      log.info(
          "Acquired lock for Keycloak user account creation for username={}, email={}",
          user.username(),
          user.email());
    } catch (OptimisticLockingFailureException e) {
      log.info(
          "Did not acquire lock for Keycloak user account creation for username={}, email={}",
          user.username(),
          user.email());
      return Optional.empty();
    }
    return Optional.of(user);
  }

  private boolean checkMatchingByUsername(RealmResource realm, UserEntity user) {
    List<UserRepresentation> search = realm.users().searchByUsername(user.username(), true);
    if (!search.isEmpty()) {
      UserRepresentation kcUser = search.get(0);
      if (!kcUser.getEmail().equals(user.email())) {
        log.error(
            "Skipping Keycloak user account creation due to mismatched email for username={}, email={}",
            user.username(),
            user.email());
        return true;
      }
    }
    return false;
  }

  private boolean checkMatchingByEmail(RealmResource realm, UserEntity user) {
    List<UserRepresentation> search = realm.users().searchByEmail(user.email(), true);
    if (!search.isEmpty()) {
      UserRepresentation kcUser = search.get(0);
      if (!kcUser.getUsername().equals(user.username())) {
        log.error(
            "Skipping Keycloak user account creation due to mismatched username for username={}, email={}",
            user.username(),
            user.email());
        return true;
      }
    }
    return false;
  }

  private UserRepresentation createUserRepresentation(UserEntity user) {
    UserRepresentation representation = new UserRepresentation();
    representation.setUsername(user.username());
    representation.setEmail(user.email());
    representation.setEnabled(true);
    representation.setEmailVerified(true);
    return representation;
  }

  private String extractKeycloakId(Response response) {
    String[] location = response.getLocation().toString().split("/");
    return location[location.length - 1];
  }

  private UserEntity setKeycloakIdAndCreatedStatus(UserEntity user, String keycloakId) {
    return new UserEntity(
        user.id(),
        user.uuid(),
        user.username(),
        user.email(),
        CreationStatus.COMPLETED,
        user.updateStatus(),
        user.update(),
        user.lock(),
        keycloakId,
        user.version());
  }

  private Optional<UserEntity> findNextIdleEntity(UserEntity user, long creationLock) {
    return userRepository.findNextByCreationLockIdle(
        CreationStatus.PENDING, creationLock - 2 * 60 * 1000, user.id());
  }
}
