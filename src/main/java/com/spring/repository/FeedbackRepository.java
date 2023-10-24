package com.spring.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spring.models.Feedback;

public interface FeedbackRepository extends MongoRepository<Feedback,String>{
    @Query(value = "{ 'createdAt' : {$gte : ?0, $lte: ?1 }}")
    List<Feedback> findByCreatedAtBetween (LocalDateTime from, LocalDateTime to);    
}
