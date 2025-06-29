package com.example.asyncaim.domain.user;

public record UsersQuery(String username) {

  private static final UsersQuery EMPTY_QUERY = new UsersQuery(null);

  public static UsersQuery empty() {
    return EMPTY_QUERY;
  }
}
