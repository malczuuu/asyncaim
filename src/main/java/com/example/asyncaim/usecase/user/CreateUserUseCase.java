package com.example.asyncaim.usecase.user;

import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.usecase.user.model.UserCreateModel;

public interface CreateUserUseCase {

  User execute(UserCreateModel request);
}
