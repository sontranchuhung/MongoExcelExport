package com.spring.dto;

import lombok.Data;

@Data
public class UserExportDTO {
    private String fileName;
    private byte[] fileData;

    public UserExportDTO(String fileName, byte[] fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }
}

