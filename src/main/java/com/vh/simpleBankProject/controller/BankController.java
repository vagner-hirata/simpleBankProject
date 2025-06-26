package com.vh.simpleBankProject.controller;

import com.vh.simpleBankProject.dto.bankAccountDTO.BankAccountDataList;
import com.vh.simpleBankProject.dto.bankAccountDTO.BankAccountDetails;
import com.vh.simpleBankProject.dto.bankAccountDTO.RegisterBankAccount;
import com.vh.simpleBankProject.dto.transferDTO.DepositMoneyDetails;
import com.vh.simpleBankProject.dto.transferDTO.RegisterTransferRequest;
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
    @PostMapping("/deposit/create")
    @Transactional
    public ResponseEntity createDepositRequest(@RequestBody @Valid RegisterTransferRequest transferRequestData, UriComponentsBuilder uriBuilder, DepositMoneyDetails depositMoneyDetails) {
        if(bankAccountService.isNotValidAccountNumber(depositMoneyDetails.toAccountNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        bankAccountService.depositMoney(depositMoneyDetails.toAccountNumber(), depositMoneyDetails.amount());
        return bankAccountService.createDepositRequest(transferRequestData, uriBuilder);
    }

//    @PostMapping("/deposit")
//    @Transactional(rollbackOn = Exception.class)
//    public ResponseEntity depositMoney(@RequestBody @Valid BankAccountDetails bankAccountDetails, DepositMoneyDetails depositMoneyDetails) {
//
//        if(bankAccountService.isNotValidAccountNumber(bankAccountDetails.accountNumber())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        bankAccountService.depositMoney(bankAccountDetails.accountNumber(), depositMoneyDetails.amount());
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }


}
