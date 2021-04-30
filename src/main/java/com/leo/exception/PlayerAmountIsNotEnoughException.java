package com.leo.exception;

public class PlayerAmountIsNotEnoughException extends RuntimeException {
    public PlayerAmountIsNotEnoughException(String msg) {
        super(msg);
    }
}
