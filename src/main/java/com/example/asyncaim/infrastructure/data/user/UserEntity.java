package com.example.asyncaim.infrastructure.data.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
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

  @Column(
      name = "user_creation_time",
      columnDefinition = "TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3)",
      insertable = false,
      updatable = false)
  private Instant creationTime;

  @Column(
      name = "user_update_time",
      columnDefinition = "TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3)",
      insertable = false,
      updatable = false)
  private Instant updateTime;

  @Column(name = "user_deletion_time", columnDefinition = "TIMESTAMP(3)")
  private Instant deletionTime;

  @Version
  @Column(name = "user_version", columnDefinition = "BIGINT DEFAULT 0", insertable = false)
  private Long version;

  /** Keep default constructor for Hibernate. */
  public UserEntity() {}

  public UserEntity(String uid, String username, String email) {
    this(null, uid, username, email, null, null, null, null);
  }

  public UserEntity(UserEntity entity) {
    this(
        entity.getId(),
        entity.getUid(),
        entity.getUsername(),
        entity.getEmail(),
        entity.getCreationTime(),
        entity.getUpdateTime(),
        entity.getDeletionTime(),
        entity.getVersion());
  }

  private UserEntity(
      Long id,
      String uid,
      String username,
      String email,
      Instant creationTime,
      Instant updateTime,
      Instant deletionTime,
      Long version) {
    this.id = id;
    this.uid = uid;
    this.username = username;
    this.email = email;
    this.creationTime = creationTime;
    this.updateTime = updateTime;
    this.deletionTime = deletionTime;
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

  public Instant getCreationTime() {
    return creationTime;
  }

  public Instant getUpdateTime() {
    return updateTime;
  }

  public Instant getDeletionTime() {
    return deletionTime;
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

  public void setDeletionTime(Instant deletionTime) {
    this.deletionTime = deletionTime;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
