package com.example.asyncaim.usecase.user.model;

import com.example.asyncaim.domain.user.User;

public class UserMapper {

  public UserListItemModel toListItemModel(User user) {
    return new UserListItemModel(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getCreateStatus(),
        user.getVersion());
  }

  public UserModel toModel(User user) {
    return new UserModel(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getCreateStatus(),
        user.getKeycloakId(),
        null,
        user.getVersion());
  }
}
