package com.example.microiam.user;

import com.example.microiam.common.PageDto;
import com.example.microiam.common.Pagination;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final KeycloakUserService keycloakUserService;
  private final UserJobExecution userJobExecution;

  public UserService(
      UserRepository userRepository,
      KeycloakUserService keycloakUserService,
      UserJobExecution userJobExecution) {
    this.userRepository = userRepository;
    this.keycloakUserService = keycloakUserService;
    this.userJobExecution = userJobExecution;
  }

  public PageDto<UserDto> getUsers(UsersQuery query, Pagination pagination) {
    Page<UserEntity> users = findUsers(query, pagination);
    Map<String, KeycloakProfileDto> keycloakUsers =
        keycloakUserService.getKeycloakUserDetails(
            users.stream()
                .map(UserEntity::keycloakId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    return new PageDto<>(
        users.stream().map(user -> toUserDto(user, keycloakUsers)).toList(),
        pagination.getPage(),
        pagination.getSize(),
        users.getTotalElements());
  }

  public UserDto requestUserCreation(CreateUserDto user) {
    UserEntity entity = createNewUserEntity(user);
    try {
      entity = userRepository.save(entity);
      userJobExecution.triggerUserCreation();
      log.info(
          "Requested creation of new user (username={}, email={})", user.username(), user.email());
      return toUserDto(entity, Map.of());
    } catch (DuplicateKeyException e) {
      log.info(
          "Unable to create new user due to duplicate key (username={}, email={}, error={})",
          user.username(),
          user.email(),
          e.getMessage());
      throw new UserConflictException();
    }
  }

  public UserDto getUser(String id) {
    UserEntity user = userRepository.findByUuid(id).orElseThrow(UserNotFoundException::new);
    Map<String, KeycloakProfileDto> keycloakProfile =
        user.keycloakId() != null
            ? keycloakUserService.getKeycloakUserDetails(List.of(user.keycloakId()))
            : Map.of();
    return toUserDto(user, keycloakProfile);
  }

  private Page<UserEntity> findUsers(UsersQuery query, Pagination pagination) {
    Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, UserEntity.USERNAME);
    return query.isPresent()
        ? userRepository.findAllByUsername(query.username(), pagination.asPageable(sortOrder))
        : userRepository.findAll(pagination.asPageable(sortOrder));
  }

  private UserDto toUserDto(UserEntity user, Map<String, KeycloakProfileDto> keycloakUsers) {
    return new UserDto(
        user.uuid(),
        user.username(),
        user.email(),
        user.creationStatus(),
        user.keycloakId(),
        user.keycloakId() != null ? keycloakUsers.getOrDefault(user.keycloakId(), null) : null,
        user.version());
  }

  private UserEntity createNewUserEntity(CreateUserDto user) {
    return new UserEntity(
        null,
        UUID.randomUUID().toString(),
        user.username(),
        user.email(),
        CreationStatus.PENDING,
        0L,
        null,
        null);
  }
}
