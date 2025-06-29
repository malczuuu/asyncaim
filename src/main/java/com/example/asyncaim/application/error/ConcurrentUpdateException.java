package com.example.asyncaim.application.error;

public class ConcurrentUpdateException extends ConflictException {
  public ConcurrentUpdateException() {
    super("concurrent update");
  }
}
