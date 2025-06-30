package com.example.asyncaim.infrastructure.decorator;

import com.example.asyncaim.application.user.UpdateUserUseCase;
import com.example.asyncaim.application.user.model.UserUpdateModel;
import com.example.asyncaim.domain.user.User;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwareUpdateUserUseCase implements UpdateUserUseCase {

  private final UpdateUserUseCase delegate;

  public TransactionAwareUpdateUserUseCase(UpdateUserUseCase delegate) {
    this.delegate = delegate;
  }

  @Transactional
  @Override
  public User execute(String id, UserUpdateModel request) {
    return delegate.execute(id, request);
  }
}
