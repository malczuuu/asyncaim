package com.example.asyncaim.application.user.impl;

import com.example.asyncaim.application.user.FindUserUseCase;
import com.example.asyncaim.application.user.error.UserNotFoundException;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;

public class FindUserUseCaseService implements FindUserUseCase {

  private final UserRepository userRepository;

  public FindUserUseCaseService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id) {
    return userRepository.findUser(id).orElseThrow(UserNotFoundException::new);
  }
}
