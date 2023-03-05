package com.example.microiam.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "microaim.realm")
public final class RealmProperties {

  private final String name;

  @ConstructorBinding
  public RealmProperties(@DefaultValue("primary") String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
