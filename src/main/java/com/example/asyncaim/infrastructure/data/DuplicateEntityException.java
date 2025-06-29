package com.example.asyncaim.infrastructure.data;

public class DuplicateEntityException extends EntityException {

  private final String field;

  public DuplicateEntityException(String message, String entity, String field, Throwable cause) {
    super(message, entity, cause);
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
