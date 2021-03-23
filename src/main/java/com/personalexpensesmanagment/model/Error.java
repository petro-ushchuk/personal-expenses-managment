package com.personalexpensesmanagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private HttpStatus status;
    private List<String> errors;
    private LocalDateTime timestamp;
}
