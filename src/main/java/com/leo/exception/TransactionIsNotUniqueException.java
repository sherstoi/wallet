package com.leo.exception;

public class TransactionIsNotUniqueException extends RuntimeException {
    public TransactionIsNotUniqueException(String msg) {
        super(msg);
    }
}
