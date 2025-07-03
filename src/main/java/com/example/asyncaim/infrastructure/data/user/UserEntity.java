package com.example.asyncaim.infrastructure.data.user;

import jakarta.persistence.*;
import org.springframework.data.annotation.Version;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "users_uid_unique_idx",
          columnNames = {"user_uid"}),
      @UniqueConstraint(
          name = "users_username_unique_idx",
          columnNames = {"user_username"}),
    })
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

  @Version
  @Column(name = "user_version", columnDefinition = "BIGINT DEFAULT 0", insertable = false)
  private Long version;

  /** Keep default constructor for Hibernate. */
  public UserEntity() {}

  public UserEntity(Long id, String uid, String username, String email, Long version) {
    this.id = id;
    this.uid = uid;
    this.username = username;
    this.email = email;
    this.version = version;
  }

  public UserEntity(String uid, String username, String email) {
    this(null, uid, username, email, null);
  }

  public UserEntity(UserEntity entity) {
    this(
        entity.getId(),
        entity.getUid(),
        entity.getUsername(),
        entity.getEmail(),
        entity.getVersion());
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

  public Long getVersion() {
    return version;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
