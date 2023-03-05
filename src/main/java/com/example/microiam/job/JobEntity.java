package com.example.microiam.job;

import java.time.Instant;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "jobs")
public class JobEntity implements Persistable<ObjectId> {
  @MongoId(FieldType.OBJECT_ID)
  private final ObjectId id;

  @Field("type")
  private String type;

  @Field("scheduleTime")
  private Instant scheduleTime;

  @Field("lockTime")
  private Instant lockTime;

  @Field("finishTime")
  private Instant finishTime;

  @Version
  @Field("version")
  private Long version;

  public JobEntity(String type, Instant scheduleTime, Instant lockTime, Instant finishTime) {
    this(null, type, scheduleTime, lockTime, finishTime, null);
  }

  public JobEntity(
      ObjectId id,
      String type,
      Instant scheduleTime,
      Instant lockTime,
      Instant finishTime,
      Long version) {
    this.id = id;
    this.type = type;
    this.scheduleTime = scheduleTime;
    this.lockTime = lockTime;
    this.finishTime = finishTime;
    this.version = version;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return getId() == null;
  }

  public String getType() {
    return type;
  }

  public Instant getScheduleTime() {
    return scheduleTime;
  }

  public Instant getLockTime() {
    return lockTime;
  }

  public Instant getFinishTime() {
    return finishTime;
  }

  public Long getVersion() {
    return version;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setScheduleTime(Instant scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  public void setLockTime(Instant lockTime) {
    this.lockTime = lockTime;
  }

  public void setFinishTime(Instant finishTime) {
    this.finishTime = finishTime;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
