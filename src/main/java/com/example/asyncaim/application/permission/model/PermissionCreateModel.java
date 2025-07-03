package com.example.asyncaim.application.permission.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PermissionCreateModel(
    @JsonProperty("name") @NotNull @Size(min = 1, max = 256) String name,
    @JsonProperty("description") @Size(max = 512) String description) {}
