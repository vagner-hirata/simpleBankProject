package com.vh.simpleBankProject.dto.transferDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositMoneyRequest(
        @NotNull
        @DecimalMin("0")
        BigDecimal amount,
        @NotNull
        String toAccountNumber
) {
}
