package com.example.microiam.user;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

  Page<UserEntity> findAllByUsername(String username, Pageable pageable);

  Optional<UserEntity> findByUuid(String uuid);

  Optional<UserEntity> findByKeycloakId(String keycloakId);

  Optional<UserEntity> findFirstByStatusOrderByIdAsc(String status);

  Optional<UserEntity> findFirstByStatusAndIdGreaterThanOrderByIdAsc(String status, ObjectId id);
}
