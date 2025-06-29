package com.example.asyncaim.application.user;

import com.example.asyncaim.application.user.model.UserUpdateModel;
import com.example.asyncaim.domain.user.User;

public interface UpdateUserUseCase {

  User execute(String id, UserUpdateModel request);
}
