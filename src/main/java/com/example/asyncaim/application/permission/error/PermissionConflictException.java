package com.example.asyncaim.application.permission.error;

import com.example.asyncaim.application.error.ConflictException;

public class PermissionConflictException extends ConflictException {

  public PermissionConflictException() {
    super("permission already exists");
  }
}
