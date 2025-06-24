package com.example.asyncaim.core.user.error;

import com.example.asyncaim.common.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("user not found");
  }
}
