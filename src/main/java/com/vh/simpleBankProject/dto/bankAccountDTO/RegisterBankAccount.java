package com.vh.simpleBankProject.dto.bankAccountDTO;

import com.vh.simpleBankProject.model.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterBankAccount(
        @NotNull
        String customerName,
        @NotNull
        BigDecimal balance,
        @NotNull
        AccountType accountType,
        @NotNull
        String accountNumber


) {
}
