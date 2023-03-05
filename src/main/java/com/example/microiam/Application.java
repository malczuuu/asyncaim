package com.example.microiam;

import com.example.microiam.keycloak.RealmProperties;
import com.example.microiam.statistic.SystemStartupEntity;
import java.time.Clock;
import org.keycloak.admin.client.Keycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  public CommandLineRunner systemStartupRunner(MongoOperations mongoOperations) {
    return args -> mongoOperations.insert(new SystemStartupEntity(clock().instant()));
  }

  @Bean
  public CommandLineRunner keycloakUsersRunner(Keycloak keycloak, RealmProperties realmProperties) {
    return args -> {
      Integer count = keycloak.realm(realmProperties.getName()).users().count();
      log.info("Connected to Keycloak and evaluated count={} users", count);
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
