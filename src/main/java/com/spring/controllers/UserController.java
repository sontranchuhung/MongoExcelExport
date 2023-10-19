package com.spring.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.spring.models.User;
import com.spring.services.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable String id) {
        Optional<User> model = userRepository.findById(id);
        if (model.isPresent()) {
            return ResponseEntity.ok(model.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<byte[]> getUsersByDateRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(startDateStr, formatter);
            endDate = LocalDate.parse(endDateStr, formatter);
        } catch (Exception badParam) {
            return ResponseEntity.badRequest().build();
        }
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<User> filteredUsers = userRepository.findByCreatedAtBetween(startDateTime, endDateTime);

        if (!filteredUsers.isEmpty()) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream);

            @SuppressWarnings("deprecation")
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("Contract", "Finished", "Email", "CreatedAt");
            try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {
                for (User user : filteredUsers) {
                    
                    //Format Date
                    String formattedDate;
                    try {
                        Date date = inputDateFormat.parse(user.getCreatedAt());
                        formattedDate = outputDateFormat.format(date);
                    } catch (ParseException e) {
                        return ResponseEntity.badRequest().build();
                    }

                    csvPrinter.printRecord(
                            user.getContract(),
                            user.getFinished(),
                            user.getEmail(),
                            formattedDate);
                }
                csvPrinter.flush();
            }

            writer.close();

            byte[] excelData = byteArrayOutputStream.toByteArray();

            // Set the response headers for Excel file download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "userdata.csv");

            // Return the Excel file as a response
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
