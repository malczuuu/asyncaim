package com.example.asyncaim.user;

public record UsersQuery(String username) {

  public boolean isPresent() {
    return username() != null;
  }
}
