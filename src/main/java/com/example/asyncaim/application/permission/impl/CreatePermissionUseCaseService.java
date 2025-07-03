package com.example.asyncaim.application.permission.impl;

import com.example.asyncaim.application.permission.CreatePermissionUseCase;
import com.example.asyncaim.application.permission.model.PermissionCreateModel;
import com.example.asyncaim.domain.identifier.IdentifierService;
import com.example.asyncaim.domain.permission.Permission;
import com.example.asyncaim.domain.permission.PermissionRepository;

public class CreatePermissionUseCaseService implements CreatePermissionUseCase {

  private final PermissionRepository permissionRepository;
  private final IdentifierService identifierService;

  public CreatePermissionUseCaseService(
      PermissionRepository permissionRepository, IdentifierService identifierService) {
    this.permissionRepository = permissionRepository;
    this.identifierService = identifierService;
  }

  @Override
  public Permission execute(PermissionCreateModel request) {
    Permission permission =
        new Permission(
            identifierService.generateIdentifier(), request.name(), request.description());
    return permissionRepository.save(permission);
  }
}
