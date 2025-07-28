package com.vh.simpleBankProject.exception;

public class TransferValueIsInvalidException extends RuntimeException {
    public TransferValueIsInvalidException(String msg) {
        super(msg);
    }
}
