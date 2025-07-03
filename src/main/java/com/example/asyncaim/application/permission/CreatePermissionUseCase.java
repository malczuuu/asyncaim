package com.example.asyncaim.application.permission;

import com.example.asyncaim.application.permission.model.PermissionCreateModel;
import com.example.asyncaim.domain.permission.Permission;

public interface CreatePermissionUseCase {

  Permission execute(PermissionCreateModel request);
}
