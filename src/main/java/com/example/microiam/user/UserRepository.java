package com.example.microiam.user;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

  Page<UserEntity> findAllByUsername(String username, Pageable pageable);

  Optional<UserEntity> findByUuid(String uuid);

  @Query("{ creationStatus : ?0, creationLock : { $lt : ?1 } }")
  Optional<UserEntity> findByCreationLockIdle(String creationStatus, long creationLock);

  @Query("{ creationStatus : ?0, creationLock : { $lt : ?1 }, id : { $gt : ?2 } }")
  Optional<UserEntity> findNextByCreationLockIdle(
      String creationStatus, long creationLock, ObjectId id);
}
