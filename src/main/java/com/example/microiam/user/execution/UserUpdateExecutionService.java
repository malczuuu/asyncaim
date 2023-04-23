package com.example.microiam.user.execution;

import com.example.microiam.keycloak.RealmProperties;
import com.example.microiam.user.UserEntity;
import com.example.microiam.user.UserRepository;
import java.time.Clock;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.NotFoundException;
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
public class UserUpdateExecutionService
    implements UserUpdateExecution, InitializingBean, DisposableBean {

  private static final long SCHEDULER_INITIAL_DELAY_SECONDS = 10;
  private static final long SCHEDULER_DELAY_SECONDS = 60;
  private static final TimeUnit SCHEDULER_UNIT = TimeUnit.SECONDS;
  private static final long LOCK_PERIOD_MILLI = 2 * 60 * 1000;

  private static final Logger log = LoggerFactory.getLogger(UserUpdateExecutionService.class);

  private final UserRepository userRepository;

  private final Clock clock;
  private final Keycloak keycloak;
  private final String realm;

  private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  public UserUpdateExecutionService(
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
        UserUpdateExecutionService.class.getSimpleName(),
        SCHEDULER_INITIAL_DELAY_SECONDS,
        SCHEDULER_DELAY_SECONDS,
        SCHEDULER_UNIT);
  }

  @Override
  public void destroy() throws Exception {
    executor.shutdown();
    while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
      log.info(
          "Awaiting termination of {} scheduler", UserUpdateExecutionService.class.getSimpleName());
    }
  }

  @Override
  public void triggerUserUpdate() {
    executor.execute(this::execute);
  }

  private void execute() {
    log.debug("Triggered execution of {}", UserUpdateExecutionService.class.getSimpleName());
    try {
      executeInternal();
    } catch (Exception e) {
      log.error("Failed to update Keycloak user account on unexpected exception", e);
    }
  }

  private void executeInternal() {
    long updateLock = clock.instant().toEpochMilli();
    Optional<UserEntity> entity = findIdleEntity(updateLock);

    while (entity.isPresent()) {
      UserEntity user = entity.get();
      log.debug(
          "Executing user update in Keycloak (realm={}, username={}, email={}, update={})",
          realm,
          user.username(),
          user.email(),
          user.update());

      Optional<UserEntity> lock = acquireLock(user, updateLock);
      if (lock.isEmpty()) {
        updateLock = clock.instant().toEpochMilli();
        entity = findNextIdleEntity(user, updateLock);
        continue;
      }
      user = lock.get();

      RealmResource realm = getKeycloakRealm();

      UserRepresentation kcUser = realm.users().get(user.keycloakId()).toRepresentation();
      kcUser.setUsername(user.update().username());
      kcUser.setEmail(user.update().email());
      try {
        realm.users().get(user.keycloakId()).update(kcUser);
      } catch (NotFoundException e) {
        log.error(
            "Failed to update Keycloak user account (realm={}, username={}, email={}, message={})",
            realm,
            user.username(),
            user.email(),
            e.getMessage());

        updateLock = clock.instant().toEpochMilli();
        entity = findNextIdleEntity(user, updateLock);
        continue;
      }

      user = setUpdatedStatus(user);
      user = userRepository.save(user);
      log.info(
          "Successfully updated user data in Keycloak (realm={}, username={}, email={})",
          realm,
          user.username(),
          user.email());

      updateLock = clock.instant().toEpochMilli();
      entity = findNextIdleEntity(user, updateLock);
    }
  }

  private Optional<UserEntity> findIdleEntity(long updateLock) {
    return userRepository.findByUpdateLockIdle(
        CreationStatus.COMPLETED, UpdateStatus.PENDING, updateLock - LOCK_PERIOD_MILLI);
  }

  private RealmResource getKeycloakRealm() {
    return keycloak.realm(realm);
  }

  private UserEntity setUpdateLock(UserEntity user, long updateLock) {
    return new UserEntity(
        user.id(),
        user.uuid(),
        user.username(),
        user.email(),
        user.createStatus(),
        user.updateStatus(),
        user.update(),
        updateLock,
        user.keycloakId(),
        user.version());
  }

  private Optional<UserEntity> acquireLock(UserEntity user, long updateLock) {
    user = setUpdateLock(user, updateLock);
    try {
      user = userRepository.save(user);
      log.info(
          "Acquired lock for Keycloak user account update (realm={}, username={}, email={})",
          realm,
          user.username(),
          user.email());
    } catch (OptimisticLockingFailureException e) {
      log.info(
          "Did not acquire lock for Keycloak user account update (realm={}, username={}, email={})",
          realm,
          user.username(),
          user.email());
      return Optional.empty();
    }
    return Optional.of(user);
  }

  private UserEntity setUpdatedStatus(UserEntity user) {
    return new UserEntity(
        user.id(),
        user.uuid(),
        user.update().username(),
        user.update().email(),
        user.createStatus(),
        UpdateStatus.IDLE,
        user.update(),
        user.lock(),
        user.keycloakId(),
        user.version());
  }

  private Optional<UserEntity> findNextIdleEntity(UserEntity user, long updateLock) {
    return userRepository.findNextByUpdateLockIdle(
        CreationStatus.COMPLETED, UpdateStatus.PENDING, updateLock - 2 * 60 * 1000, user.id());
  }
}
