package com.example.asyncaim.infrastructure.decorator.permission;

import com.example.asyncaim.application.permission.PatchPermissionUseCase;
import com.example.asyncaim.application.permission.model.PermissionPatchModel;
import com.example.asyncaim.domain.permission.Permission;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwarePatchPermissionUseCase implements PatchPermissionUseCase {

  private final PatchPermissionUseCase delegate;

  public TransactionAwarePatchPermissionUseCase(PatchPermissionUseCase delegate) {
    this.delegate = delegate;
  }

  @Transactional
  @Override
  public Permission execute(String id, PermissionPatchModel request) {
    return delegate.execute(id, request);
  }
}
