package com.example.asyncaim.infrastructure.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeycloakProfile(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email) {}
