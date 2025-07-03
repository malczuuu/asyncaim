package com.example.asyncaim.application.user.impl;

import com.example.asyncaim.application.user.InitUsersUseCase;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.domain.user.UsersQuery;

public class InitUsersUseCaseService implements InitUsersUseCase {

  private final UserRepository userRepository;

  public InitUsersUseCaseService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void execute() {
    if (userRepository.listUsers(UsersQuery.empty(), Pagination.of(0, 1)).totalElements() == 0L) {

      User admin = new User("1", "admin", "admin@example.com");
      User user = new User("2", "user", "admin@example.com");

      userRepository.save(admin);
      userRepository.save(user);
    }
  }
}
