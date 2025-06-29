package com.example.asyncaim.adapter.graphql;

import com.example.asyncaim.application.user.CreateUserUseCase;
import com.example.asyncaim.application.user.FindUserUseCase;
import com.example.asyncaim.application.user.ListUsersUseCase;
import com.example.asyncaim.application.user.model.UserCreateModel;
import com.example.asyncaim.application.user.model.UserMapper;
import com.example.asyncaim.application.user.model.UserModel;
import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.user.UsersQuery;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserGraphQlController {

  private final ListUsersUseCase listUsersUseCase;
  private final FindUserUseCase findUserUseCase;
  private final CreateUserUseCase createUserUseCase;

  private final UserMapper userMapper = new UserMapper();

  public UserGraphQlController(
      ListUsersUseCase listUsersUseCase,
      FindUserUseCase findUserUseCase,
      CreateUserUseCase createUserUseCase) {
    this.listUsersUseCase = listUsersUseCase;
    this.findUserUseCase = findUserUseCase;
    this.createUserUseCase = createUserUseCase;
  }

  @QueryMapping
  public PagedContent<UserModel> users(
      @Argument(name = "username") String username,
      @Argument(name = "page") int page,
      @Argument(name = "size") int size) {
    return listUsersUseCase
        .execute(new UsersQuery(username), new Pagination(page, size))
        .map(userMapper::toModel);
  }

  @QueryMapping
  public UserModel userById(@Argument(name = "id") String id) {
    return userMapper.toModel(findUserUseCase.execute(id));
  }

  @MutationMapping
  public UserModel createUser(@Argument UserCreateModel input) {
    return userMapper.toModel(createUserUseCase.execute(input));
  }
}
