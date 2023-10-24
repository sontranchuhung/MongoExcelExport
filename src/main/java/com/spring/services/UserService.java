package com.spring.services;

import java.util.List;

import com.spring.models.Feedback;
import com.spring.models.Session;

public interface UserService {
        List<Feedback> filterFeedbackByDateRange(String startDateStr, String endDateStr);
        List<Session> filterSessionByDateRange(String startDateStr, String endDateStr);
}
