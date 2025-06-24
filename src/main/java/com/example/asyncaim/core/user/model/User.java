package com.example.asyncaim.core.user.model;

import com.example.asyncaim.core.keycloak.KeycloakProfile;
import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email,
    @JsonProperty("createStatus") String createStatus,
    @JsonProperty("keycloakId") String keycloakId,
    @JsonProperty("keycloakProfile") KeycloakProfile keycloakProfile,
    @JsonProperty("version") Long version) {}
