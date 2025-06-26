package com.vh.simpleBankProject.service;

import com.vh.simpleBankProject.dto.bankAccountDTO.BankAccountDataList;
import com.vh.simpleBankProject.dto.bankAccountDTO.BankAccountDetails;
import com.vh.simpleBankProject.dto.bankAccountDTO.RegisterBankAccount;
import com.vh.simpleBankProject.dto.transferDTO.DepositMoneyDetails;
import com.vh.simpleBankProject.dto.transferDTO.DepositMoneyRequest;
import com.vh.simpleBankProject.dto.transferDTO.RegisterTransferRequest;
import com.vh.simpleBankProject.exception.AccountNumberAlreadyExistsException;
import com.vh.simpleBankProject.exception.AccountNumberNotFound;
import com.vh.simpleBankProject.exception.DepositValueIsInvalid;
import com.vh.simpleBankProject.model.BankAccount;
import com.vh.simpleBankProject.model.TransferHistory;
import com.vh.simpleBankProject.repository.BankAccountRepository;
import com.vh.simpleBankProject.repository.TransferHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Service
public class BankAccountService {




    private final BankAccountRepository bankAccountRepository;

    private final TransferHistoryRepository transferHistoryRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository, TransferHistoryRepository transferHistoryRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transferHistoryRepository = transferHistoryRepository;
    }


    // Method Put to create a Bank Account in the database
    public ResponseEntity createBankAccount(RegisterBankAccount bankAccountData, UriComponentsBuilder uriBuilder) {
        BankAccount bankAccount = new BankAccount(bankAccountData);
        if(bankAccountRepository.existsByAccountNumber(bankAccountData.accountNumber())) {
            // Checking if the account number already exists if it does throw an Exception
            throw new AccountNumberAlreadyExistsException("This account number already exists.");
        }
        // saving the bank account data into the database
        bankAccountRepository.save(bankAccount);
        URI uri = uriBuilder.path("/bankAccount/{id}").buildAndExpand(bankAccount.getId()).toUri();
        return ResponseEntity.created(uri).body(new BankAccountDetails(bankAccount));
    }


    public List<BankAccountDataList> getBankAccountByAccountNumber(String accountNumber) {
        isNotValidAccountNumber(accountNumber);


        return bankAccountRepository.findByAccountNumber(accountNumber).stream().map(BankAccountDataList::new).toList();



    }


    public ResponseEntity createDepositRequest(@Valid RegisterTransferRequest transferRequestData, UriComponentsBuilder uriBuilder) {
        TransferHistory transferHistory = new TransferHistory(transferRequestData);
        // checking if the amount is below zero, if it is throw an exception.
        if(transferHistory.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DepositValueIsInvalid("The deposit value is invalid, must be greater than zero");
        }
        transferHistoryRepository.save(transferHistory);
        URI uri = uriBuilder.path("/bankAccount/depositRequest/{id}").buildAndExpand(transferHistory.getId()).toUri();

        return ResponseEntity.created(uri).body(new DepositMoneyDetails(transferHistory));
    }

    public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        isNotValidAccountNumber(fromAccountNumber);
        isNotValidAccountNumber(toAccountNumber);


    }

    public void depositMoney(String accountNumber, BigDecimal amount) {

        BankAccount bankAccount = bankAccountRepository.findBankAccountByAccountNumber(accountNumber);
        bankAccount.deposit(amount);


    }

    public boolean isNotValidAccountNumber(String accountNumber) {
        if(!bankAccountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountNumberNotFound("Account number provided was not found.");
        }
        return false;
    }
}
