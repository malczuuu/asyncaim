package com.example.asyncaim.adapter.rest;

import com.example.asyncaim.application.user.CreateUserUseCase;
import com.example.asyncaim.application.user.DeleteUserUseCase;
import com.example.asyncaim.application.user.FindUserUseCase;
import com.example.asyncaim.application.user.ListUsersUseCase;
import com.example.asyncaim.application.user.PatchUserUseCase;
import com.example.asyncaim.application.user.UpdateUserUseCase;
import com.example.asyncaim.application.user.model.*;
import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UsersQuery;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserRestController {

  private final ListUsersUseCase listUsersUseCase;
  private final FindUserUseCase findUserUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final UpdateUserUseCase updateUserUseCase;
  private final PatchUserUseCase patchUserUseCase;
  private final DeleteUserUseCase deleteUserUseCase;

  private final UserMapper userMapper = new UserMapper();

  public UserRestController(
      ListUsersUseCase listUsersUseCase,
      FindUserUseCase findUserUseCase,
      CreateUserUseCase createUserUseCase,
      UpdateUserUseCase updateUserUseCase,
      PatchUserUseCase patchUserUseCase,
      DeleteUserUseCase deleteUserUseCase) {
    this.listUsersUseCase = listUsersUseCase;
    this.findUserUseCase = findUserUseCase;
    this.createUserUseCase = createUserUseCase;
    this.updateUserUseCase = updateUserUseCase;
    this.patchUserUseCase = patchUserUseCase;
    this.deleteUserUseCase = deleteUserUseCase;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PagedContent<UserListItemModel> getUsers(
      @RequestParam(name = "username", required = false) String username,
      @RequestParam(name = "page", defaultValue = "0") String page,
      @RequestParam(name = "size", defaultValue = "20") String size) {
    PagedContent<User> users =
        listUsersUseCase.execute(new UsersQuery(username), Pagination.parse(page, size));
    return users.map(userMapper::toListItemModel);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserModel getUser(@PathVariable("id") String id) {
    return userMapper.toModel(findUserUseCase.execute(id));
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public UserModel createUser(@RequestBody @Valid UserCreateModel requestBody) {
    return userMapper.toModel(createUserUseCase.execute(requestBody));
  }

  @PutMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserModel updateUser(
      @PathVariable("id") String id, @RequestBody @Valid UserUpdateModel requestBody) {
    return userMapper.toModel(updateUserUseCase.execute(id, requestBody));
  }

  @PatchMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserModel patchUser(
      @PathVariable("id") String id, @RequestBody @Valid UserPatchModel requestBody) {
    return userMapper.toModel(patchUserUseCase.execute(id, requestBody));
  }

  @DeleteMapping(path = "/{id}")
  public void deleteUser(@PathVariable("id") String id) {
    deleteUserUseCase.execute(id);
  }
}
