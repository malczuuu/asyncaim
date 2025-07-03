package com.example.asyncaim.infrastructure.data.permission;

import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.permission.Permission;
import com.example.asyncaim.domain.permission.PermissionRepository;
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
public class PermissionRepositoryJpaAdapter implements PermissionRepository {

  private static final Logger log = LoggerFactory.getLogger(PermissionRepositoryJpaAdapter.class);

  private final PermissionJpaRepository permissionJpaRepository;

  public PermissionRepositoryJpaAdapter(PermissionJpaRepository permissionJpaRepository) {
    this.permissionJpaRepository = permissionJpaRepository;
  }

  @Override
  public PagedContent<Permission> listPermissions(Pagination pagination) {
    Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "name");
    Page<PermissionEntity> permissions =
        permissionJpaRepository.findAll(pagination.asPageable(sortOrder));
    return new PagedContent<>(
        permissions.stream().map(this::toPermission).toList(),
        pagination.page(),
        pagination.size(),
        permissions.getTotalElements());
  }

  private Permission toPermission(PermissionEntity entity) {
    return new Permission(
        entity.getUid(),
        entity.getName(),
        entity.getDescription(),
        entity.getCreationTime(),
        entity.getUpdateTime(),
        entity.getVersion());
  }

  @Override
  public Optional<Permission> findPermission(String id) {
    return permissionJpaRepository.findByUid(id).map(this::toPermission);
  }

  @Transactional
  @Override
  public Permission save(Permission permission) {
    Optional<PermissionEntity> optionalEntity =
        permissionJpaRepository.findByUid(permission.getId());
    return optionalEntity
        .map(permissionEntity -> updatePermission(permission, permissionEntity))
        .orElseGet(() -> createPermission(permission));
  }

  private Permission updatePermission(Permission permission, PermissionEntity entity) {
    entity.setName(permission.getName());
    entity.setDescription(permission.getDescription());
    try {
      entity = permissionJpaRepository.save(entity);
      return toPermission(entity);
    } catch (DataIntegrityViolationException e) {
      logException(e, permission, entity, "update");
      handle(e);
      throw e;
    }
  }

  private void logException(
      DataIntegrityViolationException e,
      Permission user,
      PermissionEntity entity,
      String operation) {
    if (log.isDebugEnabled()) {
      log.info(
          "Unable to {} permission entity due to constraint violation (id={}, uid={}, update={})",
          operation,
          entity.getId(),
          entity.getUid(),
          user,
          e);
    } else {
      log.info(
          "Unable to {} permission entity due to constraint violation (id={}, uid={}, update={})",
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
          case "permission_uid_unique_idx":
            throw new DuplicateEntityException("", "permission", "id", e);
          case "permission_name_unique_idx":
            throw new DuplicateEntityException("", "permission", "name", e);
        }
      }
    }
  }

  private Permission createPermission(Permission permission) {
    PermissionEntity entity =
        new PermissionEntity(permission.getId(), permission.getName(), permission.getDescription());
    try {
      entity = permissionJpaRepository.saveAndFlush(entity);
      return toPermission(entity);
    } catch (DataIntegrityViolationException e) {
      logException(e, permission, entity, "create");
      handle(e);
      throw e;
    }
  }

  @Override
  public void delete(String id) {
    permissionJpaRepository.deleteByUid(id);
  }
}
