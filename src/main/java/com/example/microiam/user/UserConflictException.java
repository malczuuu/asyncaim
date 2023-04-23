package com.example.microiam.user;

import com.example.microiam.common.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException() {
    super("user already exists");
  }
}
