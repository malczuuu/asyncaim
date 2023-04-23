package com.example.microiam.user;

import com.example.microiam.common.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("user not found");
  }
}
