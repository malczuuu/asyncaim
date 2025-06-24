package com.example.asyncaim.entity;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Page<UserEntity> findAllByUsername(String username, Pageable pageable);

  Optional<UserEntity> findByUid(String uid);

  @Query(
      "SELECT u FROM UserEntity u WHERE u.createStatus = ?1 AND u.lock < ?2 ORDER BY u.lock DESC, u.id DESC")
  Optional<UserEntity> findFirstByCreationLockIdle(String creationStatus, long lock);
}
