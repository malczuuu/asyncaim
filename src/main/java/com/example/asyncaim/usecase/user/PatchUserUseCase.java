package com.example.asyncaim.usecase.user;

import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.usecase.user.model.UserPatchModel;

public interface PatchUserUseCase {

  User execute(String id, UserPatchModel request);
}
