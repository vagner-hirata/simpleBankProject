package com.vh.simpleBankProject.controller;

import com.vh.simpleBankProject.dto.BankAccountDataList;
import com.vh.simpleBankProject.dto.RegisterBankAccount;
import com.vh.simpleBankProject.service.BankAccountService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("{accountNumber}")
    public ResponseEntity<List<BankAccountDataList>> getBankAccountByAccountNumber(@PathVariable String accountNumber) {
        if(bankAccountService.isNotValidAccountNumber(accountNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<BankAccountDataList> getBankAccount = bankAccountService.getBankAccountByAccountNumber(accountNumber);
        return ResponseEntity.status(HttpStatus.OK).body(getBankAccount);

    }
    @PostMapping
    @Transactional
    public ResponseEntity createBankAccount(@RequestBody @Valid RegisterBankAccount bankAccountData, UriComponentsBuilder uriBuilder) {

        return bankAccountService.createBankAccount(bankAccountData, uriBuilder);

    }


}
