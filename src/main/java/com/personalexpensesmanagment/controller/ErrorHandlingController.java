package com.personalexpensesmanagment.controller;

import com.google.common.base.Throwables;
import com.personalexpensesmanagment.model.enums.ErrorCode;
import com.personalexpensesmanagment.model.enums.ErrorType;
import com.personalexpensesmanagment.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandlingController {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Error handleAllException(Exception ex) {
//        return new Error(ex.getMessage(), ErrorCode.APPLICATION_ERROR_CODE, ErrorType.FATAL_ERROR_TYPE,
//                LocalDateTime.now());
//    }

}
