package com.example.asyncaim.application.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserListItemModel(
    @JsonProperty("id") String id,
    @JsonProperty("username") String username,
    @JsonProperty("email") String email,
    @JsonProperty("createStatus") String createStatus,
    @JsonProperty("version") Long version) {}
