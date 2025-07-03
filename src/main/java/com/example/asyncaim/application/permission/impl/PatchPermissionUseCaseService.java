package com.example.asyncaim.application.permission.impl;

import com.example.asyncaim.application.permission.PatchPermissionUseCase;
import com.example.asyncaim.application.permission.error.PermissionNotFoundException;
import com.example.asyncaim.application.permission.model.PermissionPatchModel;
import com.example.asyncaim.domain.permission.Permission;
import com.example.asyncaim.domain.permission.PermissionRepository;

public class PatchPermissionUseCaseService implements PatchPermissionUseCase {

  private final PermissionRepository permissionRepository;

  public PatchPermissionUseCaseService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public Permission execute(String id, PermissionPatchModel request) {
    Permission permission =
        permissionRepository.findPermission(id).orElseThrow(PermissionNotFoundException::new);
    if (request.name() != null) {
      permission.setName(request.name());
    }
    if (request.description() != null) {
      permission.setDescription(request.description());
    }
    return permissionRepository.save(permission);
  }
}
