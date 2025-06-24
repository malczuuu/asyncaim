package com.example.asyncaim.core.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserCreate(
    @JsonProperty("username") String username, @JsonProperty("email") String email) {}
