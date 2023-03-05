package com.example.microiam.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserDto(
    @JsonProperty("username") String username, @JsonProperty("email") String email) {}
