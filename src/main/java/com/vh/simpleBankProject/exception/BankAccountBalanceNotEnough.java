package com.vh.simpleBankProject.exception;

public class BankAccountBalanceNotEnough extends RuntimeException {
    public BankAccountBalanceNotEnough(String msg) {
        super(msg);
    }
}
