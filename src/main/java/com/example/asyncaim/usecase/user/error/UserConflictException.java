package com.example.asyncaim.usecase.user.error;

import com.example.asyncaim.usecase.error.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException() {
    super("user already exists");
  }
}
