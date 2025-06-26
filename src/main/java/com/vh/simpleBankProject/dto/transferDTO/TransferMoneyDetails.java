package com.vh.simpleBankProject.dto.transferDTO;


import com.vh.simpleBankProject.model.TransferHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferMoneyDetails(
        BigDecimal amount,
        LocalDateTime timeCreated,
        String toAccountNumber,
        String fromAccountNumber

    ) {

    public TransferMoneyDetails(TransferHistory transferHistory){
        this(transferHistory.getAmount(), transferHistory.getTimeCreated(), transferHistory.getToAccountNumber(), transferHistory.getFromAccountNumber());
    }
}
