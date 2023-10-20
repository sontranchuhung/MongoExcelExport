package com.spring.services.impl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.models.User;
import com.spring.repository.UserRepository;
import com.spring.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> filterUsersByDateRange(String startDateStr, String endDateStr) {

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
        return userRepository.findByCreatedAtBetween(startDateTime, endDateTime);
    }
}
