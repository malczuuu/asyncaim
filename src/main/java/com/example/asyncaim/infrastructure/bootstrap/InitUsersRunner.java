package com.example.asyncaim.infrastructure.bootstrap;

import com.example.asyncaim.application.system.SystemInitUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitUsersRunner implements CommandLineRunner {

  private final SystemInitUseCase systemInitUseCase;

  public InitUsersRunner(SystemInitUseCase systemInitUseCase) {
    this.systemInitUseCase = systemInitUseCase;
  }

  @Override
  public void run(String... args) {
    systemInitUseCase.execute();
  }
}
