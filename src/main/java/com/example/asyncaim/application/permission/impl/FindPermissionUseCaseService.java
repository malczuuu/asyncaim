package com.example.asyncaim.application.permission.impl;

import com.example.asyncaim.application.permission.FindPermissionUseCase;
import com.example.asyncaim.application.permission.error.PermissionNotFoundException;
import com.example.asyncaim.domain.permission.Permission;
import com.example.asyncaim.domain.permission.PermissionRepository;

public class FindPermissionUseCaseService implements FindPermissionUseCase {

  private final PermissionRepository permissionRepository;

  public FindPermissionUseCaseService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public Permission execute(String id) {
    return permissionRepository.findPermission(id).orElseThrow(PermissionNotFoundException::new);
  }
}
