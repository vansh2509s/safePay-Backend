package com.self.SafePay.Service;

import com.self.SafePay.Entity.User;
import com.self.SafePay.Entity.Wallet;
import com.self.SafePay.Repository.UserRepository;
import com.self.SafePay.Repository.WalletRepository;
import com.self.SafePay.Security.CustomUserDetails;
import com.self.SafePay.Security.JwtService;
import com.self.SafePay.dto.AuthenticationResponseDto;
import com.self.SafePay.dto.LoginRequestDto;
import com.self.SafePay.dto.RegisterRequestDto;
import com.self.SafePay.dto.UserResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final SecureRandom secureRandom = new SecureRandom();

    private String generateUniqueUpiId(String fullName) {

        String baseName = fullName
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", "");

        String upiId;

        do {
            int randomNumber = 1000 + secureRandom.nextInt(9000);
            upiId = baseName + randomNumber + "@safepay";
        } while (userRepository.existsByUpiId(upiId));

        return upiId;
    }

    @Transactional
    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {

        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findByPhoneNumber(registerRequestDto.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists");
        }

        User user = new User();
        user.setFullName(registerRequestDto.getFullName());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setPhoneNumber(registerRequestDto.getPhoneNumber());

        // Generate unique UPI ID
        user.setUpiId(generateUniqueUpiId(user.getFullName()));

        Wallet wallet = new Wallet();
        wallet.setBalance(0.00);
        wallet.setUser(user);

        user.setWallet(wallet);

        // Save wallet first (if not using CascadeType.ALL)
        walletRepository.save(wallet);

        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(savedUser.getId());
        userResponseDto.setFullName(savedUser.getFullName());
        userResponseDto.setEmail(savedUser.getEmail());
        userResponseDto.setPhoneNumber(savedUser.getPhoneNumber());
        userResponseDto.setUpiId(savedUser.getUpiId());

        String jwtToken = jwtService.generateToken(new CustomUserDetails(savedUser));

        AuthenticationResponseDto response = new AuthenticationResponseDto();
        response.setToken(jwtToken);
        response.setUserResponseDto(userResponseDto);

        return response;
    }

    public AuthenticationResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setUpiId(user.getUpiId());

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));

        AuthenticationResponseDto response = new AuthenticationResponseDto();
        response.setToken(jwtToken);
        response.setUserResponseDto(userResponseDto);

        return response;
    }
}