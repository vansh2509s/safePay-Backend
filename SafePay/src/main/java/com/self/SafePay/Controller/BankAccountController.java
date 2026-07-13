package com.self.SafePay.Controller;

import com.self.SafePay.Service.BankAccountService;
import com.self.SafePay.dto.BankAccountRequestDto;
import com.self.SafePay.dto.BankAccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccountResponseDto> addBankAccount(
            @RequestBody BankAccountRequestDto bankAccountRequestDto) {

        BankAccountResponseDto response =
                bankAccountService.addBankAccount(bankAccountRequestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountResponseDto> updateBankAccount(
            @PathVariable Long id,
            @RequestBody BankAccountRequestDto bankAccountRequestDto) {

        BankAccountResponseDto response =
                bankAccountService.updateAccountDetails(id, bankAccountRequestDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankAccount(
            @PathVariable Long id) {

        bankAccountService.deleteBankAccountById(id);

        return ResponseEntity.ok("Bank account deleted successfully.");
    }
}