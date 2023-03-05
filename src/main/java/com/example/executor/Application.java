package com.example.executor;

import com.example.executor.statistic.SystemStartup;
import java.time.Clock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
public class Application {

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  public CommandLineRunner runner(MongoOperations mongoOperations) {
    return args -> {
      mongoOperations.insert(new SystemStartup(clock().instant()));
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
