package com.spring.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spring.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{ 'createdAt' : {$gte : ?0, $lte: ?1 }}")
    List<User> findByCreatedAtBetween (LocalDateTime from, LocalDateTime to);    
}
