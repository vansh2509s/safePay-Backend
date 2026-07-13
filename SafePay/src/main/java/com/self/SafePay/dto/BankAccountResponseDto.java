package com.self.SafePay.dto;

import com.self.SafePay.Enum.AccountType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BankAccountResponseDto {
    private Long id;
    private String accountHolderName;
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private String branchName;
    private Double balance;
    private AccountType accountType;
    private LocalDateTime createdAt;
}
