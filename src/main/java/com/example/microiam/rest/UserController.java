package com.example.microiam.rest;

import com.example.microiam.common.PageDto;
import com.example.microiam.common.Pagination;
import com.example.microiam.user.CreateUserDto;
import com.example.microiam.user.UserDto;
import com.example.microiam.user.UserService;
import com.example.microiam.user.UsersQuery;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<UserDto> getUsers(
      @RequestParam(name = "username", required = false) String username,
      @RequestParam(name = "page", defaultValue = "0") String page,
      @RequestParam(name = "size", defaultValue = "20") String size) {
    return userService.getUsers(new UsersQuery(username), Pagination.parse(page, size));
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto getUser(@PathVariable("id") String id) {
    return userService.getUser(id);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public UserDto requestUserCreation(@RequestBody @Valid CreateUserDto requestBody) {
    return userService.requestUserCreation(requestBody);
  }

  @PutMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public UserDto requestUserUpdate(
      @PathVariable("id") String id, @RequestBody @Valid CreateUserDto requestBody) {
    return userService.requestUserUpdate(id, requestBody);
  }
}
