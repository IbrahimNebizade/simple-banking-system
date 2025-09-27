package com.company.simplebankingsystem.controller;

import com.company.simplebankingsystem.dto.response.ErrorResponse;
import com.company.simplebankingsystem.exception.AlreadyExist;
import com.company.simplebankingsystem.exception.InsufficientBalanceException;
import com.company.simplebankingsystem.exception.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleUserNotFound(NotFoundException ex) {
        return ErrorResponse.builder()
                .status(NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(AlreadyExist.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handleProductNotFound(AlreadyExist ex) {
        return ErrorResponse.builder()
                .status(CONFLICT.value())
                .error("Already exist")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleInsufficientBalance(InsufficientBalanceException ex) {
        return ErrorResponse.builder()
                .status(BAD_REQUEST.value())
                .error("Insufficient Balance")
                .message(ex.getMessage())
                .build();
    }
}
