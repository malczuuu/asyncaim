package com.example.asyncaim.common;

public class ConcurrentUpdateException extends ConflictException {
  public ConcurrentUpdateException() {
    super("concurrent update");
  }
}
