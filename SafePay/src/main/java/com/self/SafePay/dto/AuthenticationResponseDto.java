package com.self.SafePay.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto
{
    private String token;
    private UserResponseDto userResponseDto;
}
