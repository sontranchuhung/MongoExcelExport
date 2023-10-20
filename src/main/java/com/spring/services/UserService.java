package com.spring.services;

import java.util.List;

import com.spring.models.User;

public interface UserService {
        List<User> filterUsersByDateRange(String startDateStr, String endDateStr);

}
