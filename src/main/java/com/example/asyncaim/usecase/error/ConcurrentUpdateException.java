package com.example.asyncaim.usecase.error;

public class ConcurrentUpdateException extends ConflictException {
  public ConcurrentUpdateException() {
    super("concurrent update");
  }
}
