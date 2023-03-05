package com.example.microiam.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

public record UserEntity(
    @MongoId(targetType = FieldType.OBJECT_ID) ObjectId id,
    @Field("uuid") String uuid,
    @Field(USERNAME) String username,
    @Field("keycloak_id") String keycloakId,
    @Field("email") String email,
    @Field("creation_status") String creationStatus,
    @Field("creation_lock") Long creationLock,
    @Version @Field("version") Long version) {

  public static final String USERNAME = "username";
}
