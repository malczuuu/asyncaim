package com.example.asyncaim.usecase.user;

import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UsersQuery;

public interface ListUsersUseCase {

  PagedContent<User> execute(UsersQuery query, Pagination pagination);
}
