package com.vh.simpleBankProject.repository;

import com.vh.simpleBankProject.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(String accountNumber);

    Page<BankAccount> findByAccountNumber(Pageable pageable, String accountNumber);

   BankAccount findByAccountNumber(String accountNumber);
}
