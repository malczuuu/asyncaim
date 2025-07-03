package com.example.asyncaim.application.user.model;

import com.example.asyncaim.domain.user.User;

public class UserMapper {

  public UserListItemModel toListItemModel(User user) {
    return new UserListItemModel(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getCreationTime().toString(),
        user.getUpdateTime().toString(),
        user.getVersion());
  }

  public UserModel toModel(User user) {
    return new UserModel(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getCreationTime().toString(),
        user.getUpdateTime().toString(),
        user.getVersion());
  }
}
