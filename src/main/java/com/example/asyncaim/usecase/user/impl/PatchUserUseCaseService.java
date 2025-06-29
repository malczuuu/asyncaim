package com.example.asyncaim.usecase.user.impl;

import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.usecase.user.PatchUserUseCase;
import com.example.asyncaim.usecase.user.error.UserNotFoundException;
import com.example.asyncaim.usecase.user.model.UserPatchModel;

public class PatchUserUseCaseService implements PatchUserUseCase {

  private final UserRepository userRepository;

  public PatchUserUseCaseService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id, UserPatchModel request) {
    User user = userRepository.findUser(id).orElseThrow(UserNotFoundException::new);
    if (request.username() != null) {
      user.setUsername(request.username());
    }
    if (request.email() != null) {
      user.setEmail(request.email());
    }
    return userRepository.save(user);
  }
}
