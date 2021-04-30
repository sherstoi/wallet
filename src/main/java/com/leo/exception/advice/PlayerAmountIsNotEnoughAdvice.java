package com.leo.exception.advice;

import com.leo.exception.PlayerAmountIsNotEnoughException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlayerAmountIsNotEnoughAdvice {
    @ResponseBody
    @ExceptionHandler(PlayerAmountIsNotEnoughException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    String playerAmountIsNotEnoughHandler(PlayerAmountIsNotEnoughException ex) {
        return ex.getMessage();
    }
}
