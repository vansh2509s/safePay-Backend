package com.self.SafePay.Controller;

import com.self.SafePay.Service.WalletService;
import com.self.SafePay.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<WalletResponseDto> getWallet(Authentication authentication) {

        String email = authentication.getName();

        WalletResponseDto walletResponseDto = walletService.getWallet(email);

        return ResponseEntity.ok(walletResponseDto);
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDto> getBalance(Authentication authentication) {

        String email = authentication.getName();

        BalanceResponseDto balanceResponseDto = walletService.getBalance(email);

        return ResponseEntity.ok(balanceResponseDto);
    }
    @PostMapping("/deposit")
    public ResponseEntity<DepositResponseDto> depositMoney(
            @Valid @RequestBody DepositRequestDto depositRequestDto) {

        DepositResponseDto response = walletService.depositMoney(depositRequestDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponseDto> withdrawMoney(
            @Valid @RequestBody WithdrawRequestDto withdrawRequestDto) {

        WithdrawResponseDto response = walletService.withdrawMoney(withdrawRequestDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}