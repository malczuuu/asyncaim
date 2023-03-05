package com.example.microiam.user;

import com.example.microiam.common.PageDto;
import com.example.microiam.common.Pagination;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserJobExecution userJobExecution;

  public UserService(UserRepository userRepository, UserJobExecution userJobExecution) {
    this.userRepository = userRepository;
    this.userJobExecution = userJobExecution;
  }

  public PageDto<UserDto> getUsers(UsersQuery query, Pagination pagination) {
    Page<UserEntity> users = findUsers(query, pagination);
    return new PageDto<>(
        users.stream().map(this::toUserDto).toList(),
        pagination.getPage(),
        pagination.getSize(),
        users.getTotalElements());
  }

  public UserDto requestUserCreation(CreateUserDto user) {
    UserEntity entity = createNewUserEntity(user);
    entity = userRepository.save(entity);
    userJobExecution.triggerUserCreation();
    return toUserDto(entity);
  }

  private Page<UserEntity> findUsers(UsersQuery query, Pagination pagination) {
    Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, UserEntity.USERNAME);
    return query.isPresent()
        ? userRepository.findAllByUsername(query.username(), pagination.asPageable(sortOrder))
        : userRepository.findAll(pagination.asPageable(sortOrder));
  }

  private UserDto toUserDto(UserEntity user) {
    return new UserDto(
        user.uuid(),
        user.username(),
        user.keycloakId(),
        user.email(),
        user.creationStatus(),
        user.version());
  }

  private UserEntity createNewUserEntity(CreateUserDto user) {
    return new UserEntity(
        null,
        UUID.randomUUID().toString(),
        user.username(),
        null,
        user.email(),
        CreationStatus.PENDING,
        0L,
        null);
  }
}
