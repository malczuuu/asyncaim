package com.example.asyncaim.domain.user;

import java.time.Instant;

public class User {

  private final String id;
  private String username;
  private String email;
  private final Instant creationTime;
  private final Instant updateTime;
  private final Long version;

  public User() {
    this(null, null, null);
  }

  public User(String id, String username, String email) {
    this(id, username, email, null);
  }

  public User(String id, String username, String email, Long version) {
    this(id, username, email, null, null, version);
  }

  public User(
      String id,
      String username,
      String email,
      Instant creationTime,
      Instant updateTime,
      Long version) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.creationTime = creationTime;
    this.updateTime = updateTime;
    this.version = version;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
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

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
