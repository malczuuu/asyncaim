package com.example.asyncaim.infrastructure.data;

public class EntityException extends RuntimeException {

  private final String entity;

  public EntityException(String message, String entity, Throwable cause) {
    super(message, cause);
    this.entity = entity;
  }

  public String getEntity() {
    return entity;
  }
}
