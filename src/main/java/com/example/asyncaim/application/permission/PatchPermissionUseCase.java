package com.example.asyncaim.application.permission;

import com.example.asyncaim.application.permission.model.PermissionPatchModel;
import com.example.asyncaim.domain.permission.Permission;

public interface PatchPermissionUseCase {

  Permission execute(String id, PermissionPatchModel request);
}
