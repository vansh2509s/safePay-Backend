package com.self.SafePay.dto;

import lombok.Data;

@Data
public class WithdrawRequestDto
{
    private Double amount;
    private Long bankAccountId;
}
