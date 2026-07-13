package com.self.SafePay.dto;

import lombok.Data;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.time.LocalDateTime;

@Data
public class DepositResponseDto
{
    private Long transactionId;
    private Double amount;
    private Double updatedBalance;
    private String transactionReference;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
}
