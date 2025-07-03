package com.example.asyncaim.infrastructure.data.user;

import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.domain.user.UsersQuery;
import com.example.asyncaim.infrastructure.data.DuplicateEntityException;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserRepositoryJpaAdapter implements UserRepository {

  private static final Logger log = LoggerFactory.getLogger(UserRepositoryJpaAdapter.class);

  private final UserJpaRepository userJpaRepository;

  public UserRepositoryJpaAdapter(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public PagedContent<User> listUsers(UsersQuery query, Pagination pagination) {
    Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "username");
    Page<UserEntity> users =
        userJpaRepository.findAllByUsername(query.username(), pagination.asPageable(sortOrder));
    return new PagedContent<>(
        users.stream().map(this::toUser).toList(),
        pagination.page(),
        pagination.size(),
        users.getTotalElements());
  }

  private User toUser(UserEntity entity) {
    return new User(entity.getUid(), entity.getUsername(), entity.getEmail(), entity.getVersion());
  }

  @Override
  public Optional<User> findUser(String id) {
    return userJpaRepository.findByUid(id).map(this::toUser);
  }

  @Transactional
  @Override
  public User save(User user) {
    Optional<UserEntity> optionalEntity = userJpaRepository.findByUid(user.getId());
    return optionalEntity
        .map(userEntity -> updateUserEntity(user, userEntity))
        .orElseGet(() -> createUserEntity(user));
  }

  private User updateUserEntity(User user, UserEntity entity) {
    entity.setUsername(user.getUsername());
    entity.setEmail(user.getEmail());
    entity.setVersion(user.getVersion());
    try {
      entity = userJpaRepository.save(entity);
      userJpaRepository.flush();
      return toUser(entity);
    } catch (DataIntegrityViolationException e) {
      logException(e, user, entity, "update");
      handle(e);
      throw e;
    }
  }

  private void logException(
      DataIntegrityViolationException e, User user, UserEntity entity, String operation) {
    if (log.isDebugEnabled()) {
      log.info(
          "Unable to {} user entity due to constraint violation (id={}, uid={}, update={})",
          operation,
          entity.getId(),
          entity.getUid(),
          user,
          e);
    } else {
      log.info(
          "Unable to {} user entity due to constraint violation (id={}, uid={}, update={})",
          operation,
          entity.getId(),
          entity.getUid(),
          user);
    }
  }

  private void handle(DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException ex) {
      if (ex.getConstraintName() != null) {
        switch (ex.getConstraintName()) {
          case "users_uid_unique_idx":
            throw new DuplicateEntityException("", "user", "id", e);
          case "users_username_unique_idx":
            throw new DuplicateEntityException("", "user", "username", e);
        }
      }
    }
  }

  private User createUserEntity(User user) {
    UserEntity entity = new UserEntity(user.getId(), user.getUsername(), user.getEmail());
    try {
      entity = userJpaRepository.saveAndFlush(entity);
      return toUser(entity);
    } catch (DataIntegrityViolationException e) {
      logException(e, user, entity, "create");
      handle(e);
      throw e;
    }
  }
}
