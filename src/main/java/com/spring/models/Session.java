package com.spring.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;


import lombok.Data;

@Data
public class Session {
    @Id
    private String id;
    private String chartId;
    private String contract;
    private String sessionId;
    private List<Object> finishedNodes;
    private FinishedNode current;
    private Boolean finished = false;
    private Integer currentScenario = 1;
    private String email;
    private Object analysisBefore;
    private Object analysisAfter;
    private List<Object> diagnoses;
    @CreatedDate
    private Date createdAt;

}
