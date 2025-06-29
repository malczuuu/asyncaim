package com.example.asyncaim.application.user;

import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UsersQuery;

public interface ListUsersUseCase {

  PagedContent<User> execute(UsersQuery query, Pagination pagination);
}
