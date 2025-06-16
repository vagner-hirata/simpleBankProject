package com.vh.simpleBankProject.exception;

public class AccountNumberNotFound extends RuntimeException {
    public AccountNumberNotFound(String msg) {
        super(msg);
    }
}
