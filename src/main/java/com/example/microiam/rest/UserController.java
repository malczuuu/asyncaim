package com.example.microiam.rest;

import com.example.microiam.common.PageDto;
import com.example.microiam.common.Pagination;
import com.example.microiam.user.UserDto;
import com.example.microiam.user.UserService;
import com.example.microiam.user.UsersQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
      @RequestParam(name = "username", defaultValue = "0") String page,
      @RequestParam(name = "username", defaultValue = "20") String size) {
    return userService.getUsers(new UsersQuery(username), Pagination.parse(page, size));
  }
}
