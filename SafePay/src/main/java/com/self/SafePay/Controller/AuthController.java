package com.self.SafePay.Controller;

import com.self.SafePay.Service.AuthService;
import com.self.SafePay.dto.AuthenticationResponseDto;
import com.self.SafePay.dto.LoginRequestDto;
import com.self.SafePay.dto.RegisterRequestDto;
import com.self.SafePay.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    public AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequestDto)
    {
        AuthenticationResponseDto response = authService.register(registerRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody LoginRequestDto loginRequestDto)
    {
        AuthenticationResponseDto response = authService.login(loginRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
