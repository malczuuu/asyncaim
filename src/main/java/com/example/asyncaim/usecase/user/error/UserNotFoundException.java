package com.example.asyncaim.usecase.user.error;

import com.example.asyncaim.usecase.error.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException() {
    super("user not found");
  }
}
