package com.personalexpensesmanagment.model;

import com.personalexpensesmanagment.model.enums.ErrorCode;
import com.personalexpensesmanagment.model.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private ErrorCode errorCode;
    private ErrorType errorType;
    private LocalDateTime dateTime;
}
