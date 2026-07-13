package com.self.SafePay.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto
{
    @NotBlank(message="Full name is required")
    private String fullName;
    @NotBlank(message="Email is required")
    @Email(message = "Enter a valid email id")
    private String email;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$",message ="Enter 10 Digit mobile number")
    private String phoneNumber;
    @NotBlank(message="Password is required")
    @Size(min=8, message = "Password should be atleast of 8 characters")
    private String password;
}
