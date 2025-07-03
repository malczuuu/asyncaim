package com.example.asyncaim.infrastructure.decorator.permission;

import com.example.asyncaim.application.permission.UpdatePermissionUseCase;
import com.example.asyncaim.application.permission.model.PermissionUpdateModel;
import com.example.asyncaim.domain.permission.Permission;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwareUpdatePermissionUseCase implements UpdatePermissionUseCase {

  private final UpdatePermissionUseCase delegate;

  public TransactionAwareUpdatePermissionUseCase(UpdatePermissionUseCase delegate) {
    this.delegate = delegate;
  }

  @Transactional
  @Override
  public Permission execute(String id, PermissionUpdateModel request) {
    return delegate.execute(id, request);
  }
}
