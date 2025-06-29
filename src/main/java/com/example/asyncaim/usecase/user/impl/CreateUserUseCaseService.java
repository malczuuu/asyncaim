package com.example.asyncaim.usecase.user.impl;

import com.example.asyncaim.domain.common.Status;
import com.example.asyncaim.domain.identifier.IdentifierService;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.usecase.user.CreateUserUseCase;
import com.example.asyncaim.usecase.user.model.UserCreateModel;

public class CreateUserUseCaseService implements CreateUserUseCase {

  private final UserRepository userRepository;
  private final IdentifierService identifierService;

  public CreateUserUseCaseService(
      UserRepository userRepository, IdentifierService identifierService) {
    this.userRepository = userRepository;
    this.identifierService = identifierService;
  }

  @Override
  public User execute(UserCreateModel request) {
    User user =
        new User(
            identifierService.generateIdentifier(),
            request.username(),
            request.email(),
            Status.IDLE,
            null);
    return userRepository.save(user);
  }
}
