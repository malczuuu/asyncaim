package com.example.asyncaim.usecase.error;

public class UpdatePendingException extends BadRequestException {

  public UpdatePendingException() {
    super("update pending");
  }
}
