package com.example.asyncaim.domain.user;

public class User {

  private final String id;
  private String username;
  private String email;
  private String createStatus;
  private String keycloakId;
  private final Long version;

  public User() {
    this(null, null, null, null, null);
  }

  public User(String id, String username, String email, String createStatus, String keycloakId) {
    this(id, username, email, createStatus, keycloakId, null);
  }

  public User(
      String id,
      String username,
      String email,
      String createStatus,
      String keycloakId,
      Long version) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.createStatus = createStatus;
    this.keycloakId = keycloakId;
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

  public String getCreateStatus() {
    return createStatus;
  }

  public String getKeycloakId() {
    return keycloakId;
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

  public void setCreateStatus(String createStatus) {
    this.createStatus = createStatus;
  }

  public void setKeycloakId(String keycloakId) {
    this.keycloakId = keycloakId;
  }
}
