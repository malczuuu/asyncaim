package com.example.asyncaim.application.permission.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PermissionModel(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("version") Long version) {}
