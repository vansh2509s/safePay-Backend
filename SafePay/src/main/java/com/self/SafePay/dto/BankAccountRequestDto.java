package com.self.SafePay.dto;

import com.self.SafePay.Enum.AccountType;
import lombok.Data;

@Data
public class BankAccountRequestDto
{
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private String branchName;
    private AccountType accountType;
}
