package com.vh.simpleBankProject.dto.transferDTO;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record RegisterTransferRequest(
        @DecimalMin("0")
        BigDecimal amount,
        String toAccountNumber,
        String fromAccountNumber
) {
}
