package com.example.asyncaim.application.error;

public class UpdatePendingException extends BadRequestException {

  public UpdatePendingException() {
    super("update pending");
  }
}
