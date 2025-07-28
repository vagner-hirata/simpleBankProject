package com.vh.simpleBankProject.exception;

public class BankAccountBalanceNotEnoughException extends RuntimeException {
    public BankAccountBalanceNotEnoughException(String msg) {
        super(msg);
    }
}
