package com.spring.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String  id;
    private String contract;
    private String finished;
    private String email;
    private Date createdAt;
    private String comment;
    private float score;
}

