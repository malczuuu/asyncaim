package com.example.microiam.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email,
    @JsonProperty("create_status") String createStatus,
    @JsonProperty("update_status") String updateStatus,
    @JsonProperty("update") UserUpdateDto update,
    @JsonProperty("keycloak_id") String keycloakId,
    @JsonProperty("keycloak_profile") KeycloakProfileDto keycloakProfile,
    @JsonProperty("version") Long version) {
  public record UserUpdateDto(
      @JsonProperty("username") String username, @JsonProperty("email") String email) {}
}
