package com.example.microiam.user;

public record UsersQuery(String username) {

  public boolean isPresent() {
    return username() != null;
  }
}
