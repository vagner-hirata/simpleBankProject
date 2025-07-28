package com.vh.simpleBankProject.service;

import com.vh.simpleBankProject.dto.bankAccountDTO.BankAccountDataList;
import com.vh.simpleBankProject.dto.bankAccountDTO.BankAccountDetails;
import com.vh.simpleBankProject.dto.bankAccountDTO.RegisterBankAccount;
import com.vh.simpleBankProject.dto.transferDTO.DepositMoneyDetails;
import com.vh.simpleBankProject.dto.transferDTO.RegisterTransferRequest;
import com.vh.simpleBankProject.dto.transferDTO.TransferMoneyDetails;
import com.vh.simpleBankProject.exception.AccountNumberAlreadyExistsException;
import com.vh.simpleBankProject.exception.AccountNumberNotFoundException;
import com.vh.simpleBankProject.exception.TransferValueIsInvalidException;
import com.vh.simpleBankProject.model.BankAccount;
import com.vh.simpleBankProject.model.TransferHistory;
import com.vh.simpleBankProject.repository.BankAccountRepository;
import com.vh.simpleBankProject.repository.TransferHistoryRepository;
import jakarta.validation.Valid;
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
        if(isNotValidAccountNumber(accountNumber)) {
            throw new AccountNumberNotFoundException("Account number provided was not found");
        }

        return bankAccountRepository.findByAccountNumber(accountNumber).stream().map(BankAccountDataList::new).toList();


    }


    public ResponseEntity createDepositRequest(@Valid RegisterTransferRequest transferRequestData, UriComponentsBuilder uriBuilder) {
        TransferHistory transferHistory = new TransferHistory(transferRequestData);

        // checking if the value is not too high.
        depositAmountAboveValueNeedsConfirmation(transferHistory.getAmount());
        // checking if the amount is below zero.
        transferValueIsInvalid(transferRequestData.amount());


        // saving transfer history
        transferHistoryRepository.save(transferHistory);
        URI uri = uriBuilder.path("/bankAccount/depositRequest/{id}").buildAndExpand(transferHistory.getId()).toUri();

        return ResponseEntity.created(uri).body(new DepositMoneyDetails(transferHistory));


    }

    public ResponseEntity createTransferRequest(@Valid RegisterTransferRequest transferRequestData, UriComponentsBuilder uriBuilder) {

        TransferHistory transferHistory = new TransferHistory(transferRequestData);

        // checking if account number exists in the database
        if(isNotValidAccountNumber(transferRequestData.toAccountNumber()) && isNotValidAccountNumber(transferRequestData.fromAccountNumber())) {
            throw new AccountNumberNotFoundException("Check account number, either or both of them is invalid");
        }




        // checking if amount is not above standard
        depositAmountAboveValueNeedsConfirmation(transferHistory.getAmount());

        // checking if value is not below zero or invalid
        transferValueIsInvalid(transferRequestData.amount());

        // saving transfer history in the database
        transferHistoryRepository.save(transferHistory);

        URI uri = uriBuilder.path("/bankAccount/transferRequest/{id}").buildAndExpand(transferHistory.getId()).toUri();

        return ResponseEntity.created(uri).body(new TransferMoneyDetails(transferHistory));
    }

    public void depositMoney(String accountNumber, BigDecimal amount) {

        BankAccount bankAccount = bankAccountRepository.findBankAccountByAccountNumber(accountNumber);
        bankAccount.deposit(amount);


    }
    public void transferMoney(String toAccountNumber, String fromAccountNumber, BigDecimal amount){
        BankAccount toBankAccount = bankAccountRepository.findBankAccountByAccountNumber(toAccountNumber);
        BankAccount fromBankAccount = bankAccountRepository.findBankAccountByAccountNumber(fromAccountNumber);
        toBankAccount.makeTransfer(fromBankAccount, toBankAccount, amount);

    }

    public boolean isNotValidAccountNumber(String accountNumber) {
        return !bankAccountRepository.existsByAccountNumber(accountNumber);
    }

    public void transferValueIsInvalid(BigDecimal value) {
        if(value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransferValueIsInvalidException("The deposit value is invalid, must be greater than zero");
        }

    }


    public void depositAmountAboveValueNeedsConfirmation(BigDecimal value) {
        if(value.compareTo(BigDecimal.valueOf(20000)) > 0) {
            throw new TransferValueIsInvalidException("The value you are trying to deposit is higher than the standard, you will need to confirm with our support.");
        }

    }

}
