package com.vh.simpleBankProject.exception;

public class AccountNumberAlreadyExistsException extends RuntimeException {
    public AccountNumberAlreadyExistsException(String msg) {
        super(msg);
    }
}
