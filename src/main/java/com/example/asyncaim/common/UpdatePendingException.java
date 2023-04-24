package com.example.asyncaim.common;

public class UpdatePendingException extends BadRequestException {

  public UpdatePendingException() {
    super("update pending");
  }
}
