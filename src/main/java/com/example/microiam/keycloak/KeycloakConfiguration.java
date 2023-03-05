package com.example.microiam.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({KeycloakProperties.class, RealmProperties.class})
public class KeycloakConfiguration {

  private final KeycloakProperties keycloakProperties;
  private final RealmProperties realmProperties;

  public KeycloakConfiguration(
      KeycloakProperties keycloakProperties, RealmProperties realmProperties) {
    this.keycloakProperties = keycloakProperties;
    this.realmProperties = realmProperties;
  }

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(keycloakProperties.getServerUrl())
        .realm(keycloakProperties.getRealm())
        .clientId(keycloakProperties.getClientId())
        .grantType(keycloakProperties.getGrantType())
        .username(keycloakProperties.getUsername())
        .password(keycloakProperties.getPassword())
        .build();
  }
}
