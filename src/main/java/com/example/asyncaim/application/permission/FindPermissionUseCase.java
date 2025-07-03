package com.example.asyncaim.application.permission;

import com.example.asyncaim.domain.permission.Permission;

public interface FindPermissionUseCase {

  Permission execute(String id);
}
