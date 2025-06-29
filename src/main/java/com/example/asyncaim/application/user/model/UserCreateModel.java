package com.example.asyncaim.application.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateModel(
    @JsonProperty("username") @NotNull @Size(min = 1, max = 256) String username,
    @JsonProperty("email") @NotNull @Size(min = 1, max = 512) String email) {}
