package com.example.asyncaim.application.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserListItemModel(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email,
    @JsonProperty("creationTime") String creationTime,
    @JsonProperty("updateTime") String updateTime,
    @JsonProperty("version") Long version) {}
