package com.example.asyncaim.application.permission.model;

import com.example.asyncaim.domain.permission.Permission;

public class PermissionMapper {

  public PermissionModel toModel(Permission permission) {
    return new PermissionModel(
        permission.getId(),
        permission.getName(),
        permission.getDescription(),
        permission.getVersion());
  }
}
