package com.example.asyncaim.infrastructure.configuration;

import com.example.asyncaim.domain.identifier.IdentifierService;
import com.example.asyncaim.domain.identifier.UuidIdentifierService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentifierConfiguration {

  @Bean
  public IdentifierService identifierService() {
    return new UuidIdentifierService();
  }
}
