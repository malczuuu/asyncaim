package com.example.asyncaim.core.user;

import com.example.asyncaim.common.PageDto;
import com.example.asyncaim.common.Pagination;
import com.example.asyncaim.core.keycloak.KeycloakProfile;
import com.example.asyncaim.core.keycloak.KeycloakUserService;
import com.example.asyncaim.core.keycloak.execution.CreationStatus;
import com.example.asyncaim.core.user.error.UserConflictException;
import com.example.asyncaim.core.user.error.UserNotFoundException;
import com.example.asyncaim.core.user.model.User;
import com.example.asyncaim.core.user.model.UserCreate;
import com.example.asyncaim.core.user.model.UsersQuery;
import com.example.asyncaim.entity.UserEntity;
import com.example.asyncaim.entity.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final KeycloakUserService keycloakUserService;

  public UserService(UserRepository userRepository, KeycloakUserService keycloakUserService) {
    this.userRepository = userRepository;
    this.keycloakUserService = keycloakUserService;
  }

  public PageDto<User> getUsers(UsersQuery query, Pagination pagination) {
    Page<UserEntity> users = findUsers(query, pagination);
    Map<String, KeycloakProfile> keycloakUsers =
        keycloakUserService.getKeycloakUserDetails(
            users.stream()
                .map(UserEntity::getKeycloakId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    return new PageDto<>(
        users.stream().map(user -> toUserDto(user, keycloakUsers)).toList(),
        pagination.getPage(),
        pagination.getSize(),
        users.getTotalElements());
  }

  public User getUser(String id) {
    UserEntity user = userRepository.findByUid(id).orElseThrow(UserNotFoundException::new);
    return toUserDto(user, findKeycloakProfile(user));
  }

  @Transactional
  public User requestUserCreation(UserCreate creation) {
    UserEntity entity = createNewUserEntity(creation);
    try {
      entity = userRepository.save(entity);
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

  private Page<UserEntity> findUsers(UsersQuery query, Pagination pagination) {
    Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "username");
    return query.isPresent()
        ? userRepository.findAllByUsername(query.username(), pagination.asPageable(sortOrder))
        : userRepository.findAll(pagination.asPageable(sortOrder));
  }

  private Map<String, KeycloakProfile> findKeycloakProfile(UserEntity user) {
    return user.getKeycloakId() != null
        ? keycloakUserService.getKeycloakUserDetails(List.of(user.getKeycloakId()))
        : Map.of();
  }

  private User toUserDto(UserEntity user, Map<String, KeycloakProfile> keycloakUsers) {
    return new User(
        user.getUid(),
        user.getUsername(),
        user.getEmail(),
        user.getCreateStatus(),
        user.getKeycloakId(),
        user.getKeycloakId() != null
            ? keycloakUsers.getOrDefault(user.getKeycloakId(), null)
            : null,
        user.getVersion());
  }

  private UserEntity createNewUserEntity(UserCreate user) {
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
