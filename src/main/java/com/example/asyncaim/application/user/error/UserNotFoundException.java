package com.example.asyncaim.application.user.error;

import com.example.asyncaim.application.error.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("user not found");
  }
}
