package com.vh.simpleBankProject.dto;

import com.vh.simpleBankProject.model.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterBankAccount(
        @NotNull
        String customerName,
        @DecimalMin("0")
        BigDecimal balance,
        @NotNull
        AccountType accountType,
        @NotNull
        String accountNumber


) {
}
