package com.example.asyncaim.application.permission.impl;

import com.example.asyncaim.application.permission.ListPermissionsUseCase;
import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.permission.Permission;
import com.example.asyncaim.domain.permission.PermissionRepository;

public class ListPermissionsUseCaseService implements ListPermissionsUseCase {

  private final PermissionRepository permissionRepository;

  public ListPermissionsUseCaseService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public PagedContent<Permission> execute(Pagination pagination) {
    return permissionRepository.listPermissions(pagination);
  }
}
