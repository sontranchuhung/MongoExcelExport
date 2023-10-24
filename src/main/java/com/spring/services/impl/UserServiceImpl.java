package com.spring.services.impl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.models.Feedback;
import com.spring.models.Session;
import com.spring.repository.FeedbackRepository;
import com.spring.repository.SessionRepository;
import com.spring.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public List<Feedback> filterFeedbackByDateRange(String startDateStr, String endDateStr) {

        // Input date parsing type
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(startDateStr, formatter);
            endDate = LocalDate.parse(endDateStr, formatter);
        } catch (Exception badParam) {
            return Collections.emptyList();
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return feedbackRepository.findByCreatedAtBetween(startDateTime, endDateTime);
    }

    @Override
    public List<Session> filterSessionByDateRange(String startDateStr, String endDateStr) {

        // Input date parsing type
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(startDateStr, formatter);
            endDate = LocalDate.parse(endDateStr, formatter);
        } catch (Exception badParam) {
            return Collections.emptyList();
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return sessionRepository.findByCreatedAtBetween(startDateTime, endDateTime);
    }

}
