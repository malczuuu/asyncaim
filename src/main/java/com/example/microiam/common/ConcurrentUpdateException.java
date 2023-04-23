package com.example.microiam.common;

public class ConcurrentUpdateException extends ConflictException {
  public ConcurrentUpdateException() {
    super("concurrent update");
  }
}
