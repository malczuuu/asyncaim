package com.example.asyncaim.application.permission.error;

import com.example.asyncaim.application.error.NotFoundException;

public class PermissionNotFoundException extends NotFoundException {

  public PermissionNotFoundException() {
    super("permission not found");
  }
}
