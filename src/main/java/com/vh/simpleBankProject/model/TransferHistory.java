package com.vh.simpleBankProject.model;

import com.vh.simpleBankProject.dto.transferDTO.RegisterTransferRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name="transfer_history")
@Entity(name="transfer_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")

public class TransferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDateTime timeCreated;
    private String toAccountNumber;
    private String fromAccountNumber;



    public TransferHistory(RegisterTransferRequest transferMoney) {
        this.amount = transferMoney.amount();
        this.timeCreated = LocalDateTime.now();
        this.toAccountNumber = transferMoney.toAccountNumber();
        this.fromAccountNumber = transferMoney.fromAccountNumber();
    }
}

