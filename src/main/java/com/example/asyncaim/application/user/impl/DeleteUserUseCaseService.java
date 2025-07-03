package com.example.asyncaim.application.user.impl;

import com.example.asyncaim.application.user.DeleteUserUseCase;
import com.example.asyncaim.domain.user.UserRepository;

public class DeleteUserUseCaseService implements DeleteUserUseCase {

  private final UserRepository userRepository;

  public DeleteUserUseCaseService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void execute(String id) {
    userRepository.delete(id);
  }
}
