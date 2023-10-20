package com.spring.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import com.spring.dto.UserExportDTO;
import com.spring.models.User;

@Component
public class ExcelExportUtil {

    public UserExportDTO exportUsersToExcel(List<User> users) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        if (!users.isEmpty()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream);
                 @SuppressWarnings("deprecation")
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Contract", "Finished", "Email", "CreatedAt"))) {

                for (User user : users) {
                    // Format Date
                    String formattedDate;
                    try {
                        
                        Date date = user.getCreatedAt();
                        formattedDate = dateFormat.format(date);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Error formatting date", e);
                    }

                    csvPrinter.printRecord(
                            user.getContract(),
                            user.getFinished(),
                            user.getEmail(),
                            formattedDate);
                }
                csvPrinter.flush();
            }

            byte[] excelData = byteArrayOutputStream.toByteArray();

            return new UserExportDTO("userdata.csv", excelData);

        } else {
            return new UserExportDTO("userdata.csv", new byte[0]);
        }
    }


}
