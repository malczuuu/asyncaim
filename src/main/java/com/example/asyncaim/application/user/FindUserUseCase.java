package com.example.asyncaim.application.user;

import com.example.asyncaim.domain.user.User;

public interface FindUserUseCase {

  User execute(String id);
}
