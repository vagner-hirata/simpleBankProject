package com.vh.simpleBankProject.model;

import com.vh.simpleBankProject.dto.bankAccountDTO.RegisterBankAccount;
import com.vh.simpleBankProject.exception.BankAccountBalanceNotEnough;
import com.vh.simpleBankProject.exception.DepositValueIsInvalid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Table(name="bank_account")
@Entity(name="bank_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String customerName;
    private BigDecimal balance;
    private AccountType accountType;
    // currency type, not sure how to deal with it yet probably brl 


    public BankAccount(RegisterBankAccount bankAccountData){
        this.customerName = bankAccountData.customerName();
        this.balance = bankAccountData.balance();
        this.accountType = bankAccountData.accountType();
        this.accountNumber = bankAccountData.accountNumber();




    }

    public void makeTransfer(BankAccount bankAccountTransferFrom, BankAccount bankAccountTransferTo, BigDecimal amount) {
        if(bankAccountTransferFrom.balance.compareTo(amount) < 0) {
            throw new BankAccountBalanceNotEnough("You don't have enough money to complete this transfer");
        }
        bankAccountTransferFrom.balance = bankAccountTransferFrom.balance.subtract(amount);
        bankAccountTransferTo.balance = bankAccountTransferTo.balance.add(amount);
    }

    public void deposit(BigDecimal depositValue) {
        System.out.println(this.balance);
        this.balance = this.getBalance().add(depositValue);
    }
}
