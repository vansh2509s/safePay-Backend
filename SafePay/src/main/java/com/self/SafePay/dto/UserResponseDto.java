package com.self.SafePay.dto;

import lombok.Data;

@Data
public class UserResponseDto
{
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String upiId;
}
