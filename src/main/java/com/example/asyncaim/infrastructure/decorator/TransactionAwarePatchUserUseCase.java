package com.example.asyncaim.infrastructure.decorator;

import com.example.asyncaim.application.user.PatchUserUseCase;
import com.example.asyncaim.application.user.model.UserPatchModel;
import com.example.asyncaim.domain.user.User;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwarePatchUserUseCase implements PatchUserUseCase {

  private final PatchUserUseCase delegate;

  public TransactionAwarePatchUserUseCase(PatchUserUseCase delegate) {
    this.delegate = delegate;
  }

  @Transactional
  @Override
  public User execute(String id, UserPatchModel request) {
    return delegate.execute(id, request);
  }
}
