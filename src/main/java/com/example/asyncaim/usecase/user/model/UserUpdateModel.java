package com.example.asyncaim.usecase.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateModel(
    @JsonProperty("username") @NotNull @Size(min = 1, max = 256) String username,
    @JsonProperty("email") @NotNull @Size(min = 1, max = 512) String email) {}
