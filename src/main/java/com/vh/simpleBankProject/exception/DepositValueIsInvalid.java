package com.vh.simpleBankProject.exception;

public class DepositValueIsInvalid extends RuntimeException {
    public DepositValueIsInvalid(String msg) {
        super(msg);
    }
}
