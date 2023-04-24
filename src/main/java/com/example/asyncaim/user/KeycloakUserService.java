package com.example.asyncaim.user;

import com.example.asyncaim.keycloak.RealmProperties;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KeycloakUserService {

  private static final Logger log = LoggerFactory.getLogger(KeycloakUserService.class);

  private final Keycloak keycloak;
  private final String realm;

  public KeycloakUserService(Keycloak keycloak, RealmProperties realmProperties) {
    this.keycloak = keycloak;
    this.realm = realmProperties.getName();
  }

  public Map<String, KeycloakProfileDto> getKeycloakUserDetails(Collection<String> userIds) {
    return userIds.parallelStream()
        .flatMap(userId -> getKeycloakUserDetails(userId).stream())
        .collect(Collectors.toMap(KeycloakProfileDto::id, Function.identity()));
  }

  public Optional<KeycloakProfileDto> getKeycloakUserDetails(String userId) {
    try {
      UserRepresentation user = retrieveUserRepresentationFromKeycloak(userId);
      return Optional.of(new KeycloakProfileDto(user.getId(), user.getUsername(), user.getEmail()));
    } catch (NotFoundException e) {
      log.error(
          "Attempted to retrieve unknown user from Keycloak (realm={}, userId={})", realm, userId);
      return Optional.empty();
    }
  }

  private UserRepresentation retrieveUserRepresentationFromKeycloak(String userId) {
    log.debug("Attempting to retrieve user from Keycloak (realm={}, userId={})", realm, userId);
    UserRepresentation user = getKeycloakRealm().users().get(userId).toRepresentation();
    log.debug("Successfully retrieved user from Keycloak (realm={}, userId={})", realm, userId);
    return user;
  }

  private RealmResource getKeycloakRealm() {
    return keycloak.realm(realm);
  }
}
