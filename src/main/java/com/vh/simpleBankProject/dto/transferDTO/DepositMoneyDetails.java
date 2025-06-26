package com.vh.simpleBankProject.dto.transferDTO;

import com.vh.simpleBankProject.model.TransferHistory;

import java.math.BigDecimal;

public record DepositMoneyDetails(
        BigDecimal amount,
        String toAccountNumber
) {
    public DepositMoneyDetails(TransferHistory transferHistory){
        this(transferHistory.getAmount(), transferHistory.getToAccountNumber());
    }
}
