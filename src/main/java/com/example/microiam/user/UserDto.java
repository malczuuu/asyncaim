package com.example.microiam.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email,
    @JsonProperty("creation_status") String status,
    @JsonProperty("keycloak_id") String keycloakId,
    @JsonProperty("keycloak_profile") KeycloakProfileDto keycloakProfile,
    @JsonProperty("version") Long version) {}
