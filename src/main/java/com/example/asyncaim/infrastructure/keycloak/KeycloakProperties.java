package com.example.asyncaim.infrastructure.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "asyncaim.keycloak")
public final class KeycloakProperties {

  private final String serverUrl;
  private final String realm;
  private final String clientId;
  private final String grantType;
  private final String username;
  private final String password;

  @ConstructorBinding
  public KeycloakProperties(
      @DefaultValue("http://localhost:8080") String serverUrl,
      @DefaultValue("master") String realm,
      @DefaultValue("admin-cli") String clientId,
      @DefaultValue("password") String grantType,
      @DefaultValue("admin") String username,
      @DefaultValue("password") String password) {
    this.serverUrl = serverUrl;
    this.realm = realm;
    this.clientId = clientId;
    this.grantType = grantType;
    this.username = username;
    this.password = password;
  }

  public String getServerUrl() {
    return serverUrl;
  }

  public String getRealm() {
    return realm;
  }

  public String getClientId() {
    return clientId;
  }

  public String getGrantType() {
    return grantType;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
