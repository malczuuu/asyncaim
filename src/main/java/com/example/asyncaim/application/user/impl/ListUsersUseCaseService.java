package com.example.asyncaim.application.user.impl;

import com.example.asyncaim.application.user.ListUsersUseCase;
import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.domain.user.UsersQuery;

public class ListUsersUseCaseService implements ListUsersUseCase {

  private final UserRepository userRepository;

  public ListUsersUseCaseService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public PagedContent<User> execute(UsersQuery query, Pagination pagination) {
    return userRepository.listUsers(query, pagination);
  }
}
