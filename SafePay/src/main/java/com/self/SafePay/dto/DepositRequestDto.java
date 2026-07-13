package com.self.SafePay.dto;

import lombok.Data;

@Data
public class DepositRequestDto
{
    private Long bankAccountId;
    private Double amount;
    private String remark;
}
