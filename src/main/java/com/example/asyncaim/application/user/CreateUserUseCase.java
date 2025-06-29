package com.example.asyncaim.application.user;

import com.example.asyncaim.application.user.model.UserCreateModel;
import com.example.asyncaim.domain.user.User;

public interface CreateUserUseCase {

  User execute(UserCreateModel request);
}
