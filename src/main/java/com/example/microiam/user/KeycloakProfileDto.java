package com.example.microiam.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeycloakProfileDto(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email) {}
