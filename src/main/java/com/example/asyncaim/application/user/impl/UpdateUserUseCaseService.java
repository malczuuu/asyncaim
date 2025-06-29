package com.example.asyncaim.application.user.impl;

import com.example.asyncaim.application.user.UpdateUserUseCase;
import com.example.asyncaim.application.user.error.UserNotFoundException;
import com.example.asyncaim.application.user.model.UserUpdateModel;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;

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
