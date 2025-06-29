package com.example.asyncaim.infrastructure.decorator;

import com.example.asyncaim.application.user.PatchUserUseCase;
import com.example.asyncaim.application.user.model.UserPatchModel;
import com.example.asyncaim.domain.user.User;
import org.springframework.transaction.annotation.Transactional;

public class TransactionAwarePatchUserUseCase implements PatchUserUseCase {

  private final PatchUserUseCase patchUserUseCase;

  public TransactionAwarePatchUserUseCase(PatchUserUseCase patchUserUseCase) {
    this.patchUserUseCase = patchUserUseCase;
  }

  @Transactional
  @Override
  public User execute(String id, UserPatchModel request) {
    return patchUserUseCase.execute(id, request);
  }
}
