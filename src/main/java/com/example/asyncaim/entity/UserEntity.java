package com.example.asyncaim.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Version;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_uid", nullable = false, updatable = false)
  private String uid;

  @Column(name = "user_username")
  private String username;

  @Column(name = "user_email")
  private String email;

  @Column(name = "user_create_status")
  private String createStatus;

  @Column(name = "user_lock")
  private Long lock;

  @Column(name = "user_keycloak_id")
  private String keycloakId;

  @Version
  @Column(name = "user_version")
  private Long version;

  public UserEntity() {}

  public UserEntity(
      Long id,
      String uid,
      String username,
      String email,
      String createStatus,
      Long lock,
      String keycloakId,
      Long version) {
    this.id = id;
    this.uid = uid;
    this.username = username;
    this.email = email;
    this.createStatus = createStatus;
    this.lock = lock;
    this.keycloakId = keycloakId;
    this.version = version;
  }

  public Long getId() {
    return id;
  }

  public String getUid() {
    return uid;
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

  public Long getLock() {
    return lock;
  }

  public String getKeycloakId() {
    return keycloakId;
  }

  public Long getVersion() {
    return version;
  }
}
