package com.example.asyncaim.application.permission.impl;

import com.example.asyncaim.application.permission.UpdatePermissionUseCase;
import com.example.asyncaim.application.permission.error.PermissionNotFoundException;
import com.example.asyncaim.application.permission.model.PermissionUpdateModel;
import com.example.asyncaim.domain.permission.Permission;
import com.example.asyncaim.domain.permission.PermissionRepository;

public class UpdatePermissionUseCaseService implements UpdatePermissionUseCase {

  private final PermissionRepository permissionRepository;

  public UpdatePermissionUseCaseService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public Permission execute(String id, PermissionUpdateModel request) {
    Permission permission =
        permissionRepository.findPermission(id).orElseThrow(PermissionNotFoundException::new);
    permission.setName(request.name());
    permission.setDescription(request.description());
    return permissionRepository.save(permission);
  }
}
