    package com.self.SafePay.dto;

    import lombok.Data;

    @Data
    public class AddMoneyRequestDto
    {
        private Double amount;
        private String bankAccountNumber;
    }
