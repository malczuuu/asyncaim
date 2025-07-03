package com.example.asyncaim.infrastructure.decorator.system;

import com.example.asyncaim.application.system.SystemInitUseCase;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwareSystemInitUseCase implements SystemInitUseCase {

  private final SystemInitUseCase delegate;

  public TransactionAwareSystemInitUseCase(SystemInitUseCase delegate) {
    this.delegate = delegate;
  }

  @Transactional
  @Override
  public void execute() {
    delegate.execute();
  }
}
