package com.vh.simpleBankProject.dto.bankAccountDTO;

import com.vh.simpleBankProject.model.AccountType;
import com.vh.simpleBankProject.model.BankAccount;

import java.math.BigDecimal;

public record BankAccountDataList(String accountNumber, String customerName, BigDecimal balance, AccountType accountType) {
    public BankAccountDataList(BankAccount bankAccount) {
        this(bankAccount.getAccountNumber(), bankAccount.getCustomerName(), bankAccount.getBalance(), bankAccount.getAccountType());
    }
}
