package com.example.asyncaim.application.permission.impl;

import com.example.asyncaim.application.permission.DeletePermissionUseCase;
import com.example.asyncaim.domain.permission.PermissionRepository;

public class DeletePermissionUseCaseService implements DeletePermissionUseCase {

  private final PermissionRepository permissionRepository;

  public DeletePermissionUseCaseService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public void execute(String id) {
    permissionRepository.delete(id);
  }
}
