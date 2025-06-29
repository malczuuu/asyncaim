package com.example.asyncaim.infrastructure.decorator;

import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.usecase.user.UpdateUserUseCase;
import com.example.asyncaim.usecase.user.model.UserUpdateModel;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwareUpdateUserUseCase implements UpdateUserUseCase {

  private final UpdateUserUseCase updateUserUseCase;

  public TransactionAwareUpdateUserUseCase(UpdateUserUseCase updateUserUseCase) {
    this.updateUserUseCase = updateUserUseCase;
  }

  @Transactional
  @Override
  public User execute(String id, UserUpdateModel request) {
    return updateUserUseCase.execute(id, request);
  }
}
