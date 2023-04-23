package com.example.microiam.common;

public class UpdatePendingException extends BadRequestException {

  public UpdatePendingException() {
    super("update pending");
  }
}
