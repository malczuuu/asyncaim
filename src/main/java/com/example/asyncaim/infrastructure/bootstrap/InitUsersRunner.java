package com.example.asyncaim.infrastructure.bootstrap;

import com.example.asyncaim.application.user.InitUsersUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitUsersRunner implements CommandLineRunner {

  private final InitUsersUseCase initUsersUseCase;

  public InitUsersRunner(InitUsersUseCase initUsersUseCase) {
    this.initUsersUseCase = initUsersUseCase;
  }

  @Override
  public void run(String... args) {
    initUsersUseCase.execute();
  }
}
