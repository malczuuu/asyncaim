package com.example.microiam.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "users")
public record UserEntity(
    @MongoId(targetType = FieldType.OBJECT_ID) ObjectId id,
    @Field("uuid") String uuid,
    @Field(USERNAME) String username,
    @Field("email") String email,
    @Field("create_status") String createStatus,
    @Field("update_status") String updateStatus,
    @Field("update") UserUpdate update,
    @Field("lock") Long lock,
    @Field("keycloak_id") String keycloakId,
    @Version @Field("version") Long version) {

  public static final String USERNAME = "username";

  public record UserUpdate(@Field("username") String username, @Field("email") String email) {}
}
