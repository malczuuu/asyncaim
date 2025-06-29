package com.example.asyncaim.application.user;

import com.example.asyncaim.application.user.model.UserPatchModel;
import com.example.asyncaim.domain.user.User;

public interface PatchUserUseCase {

  User execute(String id, UserPatchModel request);
}
