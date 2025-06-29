package com.example.asyncaim.usecase.user;

import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.usecase.user.model.UserUpdateModel;

public interface UpdateUserUseCase {

  User execute(String id, UserUpdateModel request);
}
