package com.example.asyncaim.core.user.model;

public record UsersQuery(String username) {

  public boolean isPresent() {
    return username() != null;
  }
}
