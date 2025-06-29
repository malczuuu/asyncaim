package com.example.asyncaim.domain.identifier;

import java.util.UUID;

public class UuidIdentifierService implements IdentifierService {

  @Override
  public String generateIdentifier() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
