package com.autozcare.main.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}