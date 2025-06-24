package com.example.asyncaim.core.user.error;

import com.example.asyncaim.common.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException() {
    super("user already exists");
  }
}
