package com.leo.exception.advice;

import com.leo.exception.TransactionIsNotUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TransactionIsNotUniqueAdvice {
    @ResponseBody
    @ExceptionHandler(TransactionIsNotUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String transactionIdIsNotUniquesHandler(TransactionIsNotUniqueException ex) {
        return ex.getMessage();
    }
}
