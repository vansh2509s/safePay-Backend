package com.self.SafePay.dto;

import com.self.SafePay.Entity.Beneficiary;
import lombok.Data;

@Data
public class TransactionRequestDto
{
    private Long beneficiaryId;
    private Double amount;
    private String remark;
}
