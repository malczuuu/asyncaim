package com.example.asyncaim.domain.permission;

import java.time.Instant;

public class Permission {

  private final String id;
  private String name;
  private String description;
  private final Instant creationTime;
  private final Instant updateTime;
  private final Long version;

  public Permission(String id, String name, String description) {
    this(id, name, description, null, null, null);
  }

  public Permission(
      String id,
      String name,
      String description,
      Instant creationTime,
      Instant updateTime,
      Long version) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.creationTime = creationTime;
    this.updateTime = updateTime;
    this.version = version;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Instant getCreationTime() {
    return creationTime;
  }

  public Instant getUpdateTime() {
    return updateTime;
  }

  public Long getVersion() {
    return version;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
