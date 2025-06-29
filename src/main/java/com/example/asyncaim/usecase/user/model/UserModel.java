package com.example.asyncaim.usecase.user.model;

import com.example.asyncaim.infrastructure.keycloak.KeycloakProfile;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserModel(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email,
    @JsonProperty("createStatus") String createStatus,
    @JsonProperty("keycloakId") String keycloakId,
    @JsonProperty("keycloakProfile") KeycloakProfile keycloakProfile,
    @JsonProperty("version") Long version) {}
