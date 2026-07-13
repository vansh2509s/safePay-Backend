package com.self.SafePay.dto;

import lombok.Data;
import com.self.SafePay.Enum.TransactionStatus;

import java.time.LocalDateTime;

@Data
public class WithdrawResponseDto {

    private Long transactionId;

    private Double amount;

    private Double remainingBalance;

    private String transactionReference;

    private TransactionStatus transactionStatus;

    private LocalDateTime createdAt;
}
