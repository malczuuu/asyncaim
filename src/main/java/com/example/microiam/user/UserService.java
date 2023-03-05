package com.example.microiam.user;

import com.example.microiam.common.PageDto;
import com.example.microiam.common.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public PageDto<UserDto> getUsers(UsersQuery query, Pagination pagination) {
    Page<UserEntity> users = findUsers(query, pagination);
    return new PageDto<>(
        users.stream().map(this::toUserDto).toList(),
        pagination.getPage(),
        pagination.getSize(),
        users.getTotalElements());
  }

  private Page<UserEntity> findUsers(UsersQuery query, Pagination pagination) {
    Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, UserEntity.USERNAME);
    return query.isPresent()
        ? userRepository.findAll(pagination.asPageable(sortOrder))
        : userRepository.findAllByUsername(query.username(), pagination.asPageable(sortOrder));
  }

  private UserDto toUserDto(UserEntity user) {
    return new UserDto(
        user.uuid(),
        user.username(),
        user.keycloakId(),
        user.email(),
        user.status(),
        user.version());
  }
}
