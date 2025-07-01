package com.example.asyncaim.infrastructure.decorator;

import com.example.asyncaim.application.user.InitUsersUseCase;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwareInitUsersUseCase implements InitUsersUseCase {

  private final InitUsersUseCase delegate;

  public TransactionAwareInitUsersUseCase(InitUsersUseCase delegate) {
    this.delegate = delegate;
  }

  @Transactional
  @Override
  public void execute() {
    delegate.execute();
  }
}
