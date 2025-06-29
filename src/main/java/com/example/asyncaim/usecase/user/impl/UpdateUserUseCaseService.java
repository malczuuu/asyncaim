package com.example.asyncaim.usecase.user.impl;

import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.usecase.user.UpdateUserUseCase;
import com.example.asyncaim.usecase.user.error.UserNotFoundException;
import com.example.asyncaim.usecase.user.model.UserUpdateModel;

public class UpdateUserUseCaseService implements UpdateUserUseCase {

  private final UserRepository userRepository;

  public UpdateUserUseCaseService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id, UserUpdateModel request) {
    User user = userRepository.findUser(id).orElseThrow(UserNotFoundException::new);
    user.setUsername(request.username());
    user.setEmail(request.email());
    return userRepository.save(user);
  }
}
