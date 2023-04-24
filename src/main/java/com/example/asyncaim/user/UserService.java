package com.example.asyncaim.user;

import com.example.asyncaim.common.ConcurrentUpdateException;
import com.example.asyncaim.common.PageDto;
import com.example.asyncaim.common.Pagination;
import com.example.asyncaim.common.UpdatePendingException;
import com.example.asyncaim.user.execution.CreationStatus;
import com.example.asyncaim.user.execution.UpdateStatus;
import com.example.asyncaim.user.execution.UserCreateExecution;
import com.example.asyncaim.user.execution.UserUpdateExecution;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final KeycloakUserService keycloakUserService;
  private final UserCreateExecution userCreateExecution;
  private final UserUpdateExecution userUpdateExecution;

  public UserService(
      UserRepository userRepository,
      KeycloakUserService keycloakUserService,
      UserCreateExecution userCreateExecution,
      UserUpdateExecution userUpdateExecution) {
    this.userRepository = userRepository;
    this.keycloakUserService = keycloakUserService;
    this.userCreateExecution = userCreateExecution;
    this.userUpdateExecution = userUpdateExecution;
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

  public UserDto getUser(String id) {
    UserEntity user = userRepository.findByUuid(id).orElseThrow(UserNotFoundException::new);
    return toUserDto(user, findKeycloakProfile(user));
  }

  public UserDto requestUserCreation(CreateUserDto creation) {
    UserEntity entity = createNewUserEntity(creation);
    try {
      entity = userRepository.save(entity);
      userCreateExecution.triggerUserCreation();
      log.info("Requested creation of new user (creation={})", creation);
      return toUserDto(entity, Map.of());
    } catch (DuplicateKeyException e) {
      log.info(
          "Unable to create new user due to duplicate key (username={}, email={}, error={})",
          creation.username(),
          creation.email(),
          e.getMessage());
      throw new UserConflictException();
    }
  }

  public UserDto requestUserUpdate(String id, CreateUserDto update) {
    UserEntity entity = userRepository.findByUuid(id).orElseThrow(UserNotFoundException::new);

    if (UpdateStatus.PENDING.equals(entity.updateStatus())) {
      throw new UpdatePendingException();
    }

    entity = updateUserEntity(entity, update);
    try {
      entity = userRepository.save(entity);
      log.info(
          "Requested update of user (uuid={}, username={}, email={}, update={})",
          entity.uuid(),
          entity.username(),
          entity.email(),
          update);
      userUpdateExecution.triggerUserUpdate();
      return toUserDto(entity, findKeycloakProfile(entity));
    } catch (OptimisticLockingFailureException e) {
      log.info(
          "Unable to update user due to optimistic locking failure (uuid={}, username={}, email={}, update={})",
          entity.uuid(),
          entity.username(),
          entity.email(),
          update);
      throw new ConcurrentUpdateException();
    }
  }

  private Page<UserEntity> findUsers(UsersQuery query, Pagination pagination) {
    Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, UserEntity.USERNAME);
    return query.isPresent()
        ? userRepository.findAllByUsername(query.username(), pagination.asPageable(sortOrder))
        : userRepository.findAll(pagination.asPageable(sortOrder));
  }

  private Map<String, KeycloakProfileDto> findKeycloakProfile(UserEntity user) {
    return user.keycloakId() != null
        ? keycloakUserService.getKeycloakUserDetails(List.of(user.keycloakId()))
        : Map.of();
  }

  private UserDto toUserDto(UserEntity user, Map<String, KeycloakProfileDto> keycloakUsers) {
    return new UserDto(
        user.uuid(),
        user.username(),
        user.email(),
        user.createStatus(),
        user.updateStatus(),
        user.update() != null
            ? new UserDto.UserUpdateDto(user.update().username(), user.update().email())
            : null,
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
        UpdateStatus.IDLE,
        null,
        0L,
        null,
        null);
  }

  private UserEntity updateUserEntity(UserEntity entity, CreateUserDto user) {
    return new UserEntity(
        entity.id(),
        entity.uuid(),
        entity.username(),
        entity.email(),
        entity.createStatus(),
        UpdateStatus.PENDING,
        new UserEntity.UserUpdate(user.username(), user.email()),
        entity.lock(),
        entity.keycloakId(),
        entity.version());
  }
}
