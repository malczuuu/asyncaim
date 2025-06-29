package com.example.asyncaim.usecase.user.impl;

import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.usecase.user.FindUserUseCase;
import com.example.asyncaim.usecase.user.error.UserNotFoundException;

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
