package com.example.asyncaim.application.user.error;

import com.example.asyncaim.application.error.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException() {
    super("user already exists");
  }
}
