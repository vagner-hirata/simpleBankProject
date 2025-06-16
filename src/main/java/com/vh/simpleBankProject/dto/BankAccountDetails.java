package com.vh.simpleBankProject.dto;

import com.vh.simpleBankProject.model.AccountType;
import com.vh.simpleBankProject.model.BankAccount;

import java.math.BigDecimal;

public record BankAccountDetails(
    Long id,
    String customerName,
    String accountNumber,
    BigDecimal balance,
    AccountType accountType
            )
{
    public BankAccountDetails(BankAccount bankAccount) {
        this(bankAccount.getId(), bankAccount.getAccountNumber(), bankAccount.getCustomerName(), bankAccount.getBalance(), bankAccount.getAccountType());
    }
}