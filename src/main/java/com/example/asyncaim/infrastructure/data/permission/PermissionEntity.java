package com.example.asyncaim.infrastructure.data.permission;

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
    name = "permissions",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "permissions_uid_unique_idx",
          columnNames = {"permission_uid"}),
      @UniqueConstraint(
          name = "permissions_name_unique_idx",
          columnNames = {"permission_name"}),
    })
public class PermissionEntity {

  @Id
  @Column(name = "permission_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "permission_uid", nullable = false, updatable = false)
  private String uid;

  @Column(name = "permission_name", nullable = false)
  private String name;

  @Column(name = "permission_description")
  private String description;

  @Column(
      name = "permission_creation_time",
      columnDefinition = "TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3)",
      insertable = false,
      updatable = false)
  private Instant creationTime;

  @Column(
      name = "permission_update_time",
      columnDefinition = "TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3)",
      insertable = false,
      updatable = false)
  private Instant updateTime;

  @Version
  @Column(name = "permission_version", columnDefinition = "BIGINT DEFAULT 0", insertable = false)
  private Long version;

  public PermissionEntity() {}

  public PermissionEntity(String uid, String name, String description) {
    this(null, uid, name, description, null, null, null);
  }

  public PermissionEntity(PermissionEntity permission) {
    this(
        permission.getId(),
        permission.getUid(),
        permission.getName(),
        permission.getDescription(),
        permission.getCreationTime(),
        permission.getUpdateTime(),
        permission.getVersion());
  }

  private PermissionEntity(
      Long id,
      String uid,
      String name,
      String description,
      Instant creationTime,
      Instant updateTime,
      Long version) {
    this.id = id;
    this.uid = uid;
    this.name = name;
    this.description = description;
    this.creationTime = creationTime;
    this.updateTime = updateTime;
    this.version = version;
  }

  public Long getId() {
    return id;
  }

  public String getUid() {
    return uid;
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
