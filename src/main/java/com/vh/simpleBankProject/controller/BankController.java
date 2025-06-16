package com.vh.simpleBankProject.controller;

import com.vh.simpleBankProject.dto.RegisterBankAccount;
import com.vh.simpleBankProject.service.BankAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity getBankAccountByAccountNumber(Pageable pageable, String accountNumber) {

        return bankAccountService.getBankAccountByAccountNumber(pageable, accountNumber);

    }
    @PostMapping
    @Transactional
    public ResponseEntity createBankAccount(RegisterBankAccount bankAccountData, UriComponentsBuilder uriBuilder) {

        return bankAccountService.createBankAccount(bankAccountData, uriBuilder);

    }


}
