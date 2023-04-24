package com.example.asyncaim.user;

import com.example.asyncaim.common.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("user not found");
  }
}
