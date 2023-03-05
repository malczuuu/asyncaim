package com.example.microiam.user;

import com.example.microiam.keycloak.RealmProperties;
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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class UserJobExecution implements InitializingBean {

  private static final Logger log = LoggerFactory.getLogger(UserJobExecution.class);

  private final UserRepository userRepository;

  private final Keycloak keycloak;
  private final RealmProperties realmProperties;

  private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  public UserJobExecution(
      UserRepository userRepository, Keycloak keycloak, RealmProperties realmProperties) {
    this.userRepository = userRepository;
    this.keycloak = keycloak;
    this.realmProperties = realmProperties;
  }

  @Override
  public void afterPropertiesSet() {
    executor.scheduleWithFixedDelay(this::triggerUserCreation, 30, 60, TimeUnit.SECONDS);
  }

  public void triggerUserCreation() {
    Optional<UserEntity> entity = userRepository.findFirstByStatusOrderByIdAsc("idle");
    while (entity.isPresent()) {
      UserEntity user = entity.get();

      RealmResource realm = keycloak.realm(realmProperties.getName());

      UserRepresentation kcUser = new UserRepresentation();
      kcUser.setUsername(user.username());
      kcUser.setEmail(user.email());
      kcUser.setEnabled(true);
      kcUser.setEmailVerified(true);

      try (Response response = realm.users().create(kcUser)) {
        if (response.getStatus() / 100 == 2) {
          log.info(
              "Successfully created Keycloak user account for username={}, email={}",
              user.username(),
              user.email());

          String[] location = response.getLocation().toString().split("/");
          String keycloakId = location[location.length - 1];
          user =
              new UserEntity(
                  user.id(),
                  user.uuid(),
                  user.username(),
                  keycloakId,
                  user.email(),
                  "created",
                  user.version());
          user = userRepository.save(user);
        } else {
          log.error(
              "Failed to create Keycloak user account for username={}, email={}",
              user.username(),
              user.email());
        }
      }

      entity = userRepository.findFirstByStatusAndIdGreaterThanOrderByIdAsc("idle", user.id());
    }
  }
}
