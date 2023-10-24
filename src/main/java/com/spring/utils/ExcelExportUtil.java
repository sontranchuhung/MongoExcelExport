package com.spring.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import com.spring.dto.UserExportDTO;
import com.spring.models.Feedback;
import com.spring.models.Session;

@Component
public class ExcelExportUtil {

    public UserExportDTO exportSessionToExcel(List<Session> sessions) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        if (!sessions.isEmpty()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream);
                 @SuppressWarnings("deprecation")
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Contract", "Finished", "Email", "CreatedAt", "AnalysisBefore", "AnalysisAfter", "Diagnoses"))) {

                for (Session session : sessions) {
                    // Format Date
                    String formattedDate;
                    try {
                        Date date = session.getCreatedAt();
                        formattedDate = dateFormat.format(date);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Error formatting date", e);
                    }
                    if (session.getDiagnoses() != null) {
                        session.getDiagnoses();
                    }

                    csvPrinter.printRecord(
                            session.getContract(),
                            session.getFinished(),
                            session.getEmail(),
                            formattedDate,
                            session.getAnalysisBefore(),
                            session.getAnalysisAfter(),
                            session.getDiagnoses()
                            );
                }
                csvPrinter.flush();
            }

            byte[] excelData = byteArrayOutputStream.toByteArray();

            return new UserExportDTO("userdata.csv", excelData);

        } else {
            return new UserExportDTO("userdata.csv", new byte[0]);
        }
    }

    public UserExportDTO exportFeedbackToExcel(List<Feedback> feedbacks) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        if (!feedbacks.isEmpty()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
                 @SuppressWarnings("deprecation")
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("\ufeff"+"Contract", "Email", "Score", "Comment", "CreatedAt"))) {

                for (Feedback feedback : feedbacks) {
                    // Format Date
                    String formattedDate;
                    try {
                        
                        Date date = feedback.getCreatedAt();
                        formattedDate = dateFormat.format(date);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Error formatting date", e);
                    }

                    csvPrinter.printRecord(
                            feedback.getContract(),
                            feedback.getEmail(),
                            feedback.getScore(),
                            feedback.getComment(),
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
