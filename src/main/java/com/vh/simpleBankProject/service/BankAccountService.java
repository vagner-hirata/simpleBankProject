package com.vh.simpleBankProject.service;

import com.vh.simpleBankProject.dto.BankAccountDataList;
import com.vh.simpleBankProject.dto.BankAccountDetails;
import com.vh.simpleBankProject.dto.RegisterBankAccount;
import com.vh.simpleBankProject.exception.AccountNumberAlreadyExistsException;
import com.vh.simpleBankProject.exception.AccountNumberNotFound;
import com.vh.simpleBankProject.model.BankAccount;
import com.vh.simpleBankProject.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Service
public class BankAccountService {



    @Autowired
    private BankAccountRepository bankAccountRepository;


    // Method Put to create a Bank Account in the database
    public ResponseEntity createBankAccount(RegisterBankAccount bankAccountData, UriComponentsBuilder uriBuilder) {
        BankAccount bankAccount = new BankAccount(bankAccountData);
        if(bankAccountRepository.existsByAccountNumber(bankAccountData.accountNumber())) {
            // Checking if the account number already exists if it does throw an Exception
            throw new AccountNumberAlreadyExistsException("This account number already exists.");
        }
        bankAccountRepository.save(bankAccount);
        URI uri = uriBuilder.path("/bankAccount/{id}").buildAndExpand(bankAccount.getId()).toUri();
        return ResponseEntity.created(uri).body(new BankAccountDetails(bankAccount));
    }


    public List<BankAccountDataList> getBankAccountByAccountNumber(String accountNumber) {
        isNotValidAccountNumber(accountNumber);


        return bankAccountRepository.findByAccountNumber(accountNumber).stream().map(BankAccountDataList::new).toList();



    }

    @Transactional(rollbackOn = Exception.class)
    public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        isNotValidAccountNumber(fromAccountNumber);
        isNotValidAccountNumber(toAccountNumber);







    }

    public boolean isNotValidAccountNumber(String accountNumber) {
        if(!bankAccountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountNumberNotFound("Account number provided was not found.");
        }
        return false;
    }
}
