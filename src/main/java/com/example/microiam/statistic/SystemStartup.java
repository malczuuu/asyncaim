package com.example.microiam.statistic;

import java.time.Instant;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "system_startups")
public class SystemStartup implements Persistable<ObjectId> {

  @MongoId(targetType = FieldType.OBJECT_ID)
  private final ObjectId id;

  @Field("time")
  private Instant time;

  public SystemStartup() {
    this(null);
  }

  public SystemStartup(Instant time) {
    this(null, time);
  }

  public SystemStartup(ObjectId id, Instant time) {
    this.id = id;
    this.time = time;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return getId() == null;
  }

  public void setTime(Instant time) {
    this.time = time;
  }

  public Instant getTime() {
    return time;
  }
}
