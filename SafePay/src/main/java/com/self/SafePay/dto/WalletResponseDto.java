package com.self.SafePay.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletResponseDto
{
    private Long id;
    private Double  balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
