package com.example.asyncaim.application.user.impl;

import com.example.asyncaim.application.user.PatchUserUseCase;
import com.example.asyncaim.application.user.error.UserNotFoundException;
import com.example.asyncaim.application.user.model.UserPatchModel;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;

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
