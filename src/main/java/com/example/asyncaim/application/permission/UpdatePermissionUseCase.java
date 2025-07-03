package com.example.asyncaim.application.permission;

import com.example.asyncaim.application.permission.model.PermissionUpdateModel;
import com.example.asyncaim.domain.permission.Permission;

public interface UpdatePermissionUseCase {

  Permission execute(String id, PermissionUpdateModel request);
}
