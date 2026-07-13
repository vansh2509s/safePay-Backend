package com.self.SafePay.dto;

import com.self.SafePay.Enum.TransactionStatus;
import com.self.SafePay.Enum.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponseDto
{
    private Long id;
    private Double amount;
    private String remark;
    private String transactionReference;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
    private String senderName;
    private String receiverName;
    private String receiverUpiId;
}
