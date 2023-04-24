package com.example.asyncaim.user;

import com.example.asyncaim.common.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException() {
    super("user already exists");
  }
}
