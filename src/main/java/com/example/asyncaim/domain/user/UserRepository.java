package com.example.asyncaim.domain.user;

import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import java.util.Optional;

public interface UserRepository {

  PagedContent<User> listUsers(UsersQuery query, Pagination pagination);

  Optional<User> findUser(String id);

  User save(User user);
}
