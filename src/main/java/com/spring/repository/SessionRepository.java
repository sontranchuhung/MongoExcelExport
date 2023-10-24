package com.spring.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spring.models.Session;

public interface SessionRepository extends MongoRepository<Session,String>{
    @Query(value = "{ 'createdAt' : {$gte : ?0, $lte: ?1 }}")
    List<Session> findByCreatedAtBetween (LocalDateTime from, LocalDateTime to);    
}
