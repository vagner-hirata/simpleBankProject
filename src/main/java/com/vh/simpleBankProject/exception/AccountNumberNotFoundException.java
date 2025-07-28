package com.vh.simpleBankProject.exception;

public class AccountNumberNotFoundException extends RuntimeException {
    public AccountNumberNotFoundException(String msg) {
        super(msg);
    }
}
