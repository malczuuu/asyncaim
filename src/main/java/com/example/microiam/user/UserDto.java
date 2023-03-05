package com.example.microiam.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("keycloak_id") String keycloakId,
    @JsonProperty("email") String email,
    @JsonProperty("status") String status,
    @JsonProperty("version") Long version) {}
