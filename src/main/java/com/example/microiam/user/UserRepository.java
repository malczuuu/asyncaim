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

  @Query("{ creationStatus : ?0, lock : { $lt : ?1 } }")
  Optional<UserEntity> findByCreationLockIdle(String creationStatus, long lock);

  @Query("{ creationStatus : ?0, lock : { $lt : ?1 }, id : { $gt : ?2 } }")
  Optional<UserEntity> findNextByCreationLockIdle(String creationStatus, long lock, ObjectId id);

  @Query("{ creationStatus : ?0, updateStatus: ?1, lock : { $lt : ?2 } }")
  Optional<UserEntity> findByUpdateLockIdle(String creationStatus, String updateStatus, long lock);

  @Query("{ creationStatus : ?0, updateStatus : ?1, lock : { $lt : ?2 }, id : { $gt : ?3 } }")
  Optional<UserEntity> findNextByUpdateLockIdle(
      String creationStatus, String updateStatus, long lock, ObjectId id);
}
