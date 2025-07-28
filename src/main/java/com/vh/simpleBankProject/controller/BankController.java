package com.vh.simpleBankProject.controller;

import com.vh.simpleBankProject.dto.bankAccountDTO.BankAccountDataList;
import com.vh.simpleBankProject.dto.bankAccountDTO.RegisterBankAccount;
import com.vh.simpleBankProject.dto.transferDTO.RegisterTransferRequest;
import com.vh.simpleBankProject.service.BankAccountService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/bank")
public class BankController {


    private final BankAccountService bankAccountService;

    public BankController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

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
    public ResponseEntity createDepositRequest(@RequestBody @Valid RegisterTransferRequest transferRequestData, UriComponentsBuilder uriBuilder) {
        if(bankAccountService.isNotValidAccountNumber(transferRequestData.toAccountNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        bankAccountService.depositMoney(transferRequestData.toAccountNumber(), transferRequestData.amount());
        return bankAccountService.createDepositRequest(transferRequestData, uriBuilder);
    }
    @PostMapping("/transfer/create")
    @Transactional
    public ResponseEntity transferMoneyRequest(@RequestBody @Valid RegisterTransferRequest transferRequestData, UriComponentsBuilder uriBuilder ) {

        bankAccountService.transferMoney(transferRequestData.toAccountNumber(), transferRequestData.fromAccountNumber(), transferRequestData.amount());
        return bankAccountService.createTransferRequest(transferRequestData, uriBuilder);

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
