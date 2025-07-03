package com.example.asyncaim.infrastructure.data.permission;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long> {

  Optional<PermissionEntity> findByUid(String uid);

  void deleteByUid(String uid);
}
