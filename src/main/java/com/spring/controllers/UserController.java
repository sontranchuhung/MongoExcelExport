package com.spring.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

import com.spring.dto.UserExportDTO;
import com.spring.models.Feedback;
import com.spring.models.Session;

import com.spring.services.UserService;
import com.spring.utils.ExcelExportUtil;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ExcelExportUtil excelExportUtil;

    @GetMapping("/session")
    public ResponseEntity<Resource> getFeedbackByDateRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) throws IOException {

        List<Session> filteredUsers = userService.filterSessionByDateRange(startDateStr, endDateStr);
        UserExportDTO userExportResponse = excelExportUtil.exportSessionToExcel(filteredUsers);

        ByteArrayResource resource = new ByteArrayResource(userExportResponse.getFileData());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + userExportResponse.getFileName())
            .contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType("application/csv"))
            .body(resource);
    }

    @GetMapping("/feedback")
    public ResponseEntity<Resource> getSessionByDateRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) throws IOException {

        List<Feedback> filteredUsers = userService.filterFeedbackByDateRange(startDateStr, endDateStr);
        UserExportDTO userExportResponse = excelExportUtil.exportFeedbackToExcel(filteredUsers);

        ByteArrayResource resource = new ByteArrayResource(userExportResponse.getFileData());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + userExportResponse.getFileName())
            .contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType("application/csv"))
            .body(resource);
    }
}
