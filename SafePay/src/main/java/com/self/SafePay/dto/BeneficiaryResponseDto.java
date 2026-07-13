package com.self.SafePay.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BeneficiaryResponseDto
{
    private Long id;
    private String nickName;
    private String benificaryName;
    private String upiId;
    private LocalDateTime createdDate;
}
