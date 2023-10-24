package com.spring.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Feedback {
    @Id
    private String id;
    private String sessionId;
    private String contract;
    private String email;
    private Double score;
    private String comment;
    @CreatedDate
    private Date createdAt;
}
