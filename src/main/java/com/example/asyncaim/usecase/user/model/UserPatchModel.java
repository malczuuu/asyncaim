package com.example.asyncaim.usecase.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record UserPatchModel(
    @JsonProperty("username") @Size(min = 1, max = 256) String username,
    @JsonProperty("email") @Size(min = 1, max = 512) String email) {}
