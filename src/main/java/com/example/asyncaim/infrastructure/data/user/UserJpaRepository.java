package com.example.asyncaim.infrastructure.data.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

  @Query(
      "SELECT u FROM UserEntity u WHERE (?1 IS NULL OR u.username LIKE ?1%) AND u.deletionTime IS NULL")
  Page<UserEntity> findAllByUsername(String username, Pageable pageable);

  @Query("SELECT u FROM UserEntity u WHERE u.uid = ?1 AND u.deletionTime IS NULL")
  Optional<UserEntity> findByUid(String uid);
}
