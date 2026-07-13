package com.self.SafePay.Controller;

import com.self.SafePay.Service.TransactionService;
import com.self.SafePay.dto.TransactionRequestDto;
import com.self.SafePay.dto.TransactionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // Send Money
    @PostMapping("/send")
    public ResponseEntity<TransactionResponseDto> sendMoney(
            @Valid @RequestBody TransactionRequestDto transactionRequestDto) {

        TransactionResponseDto response =
                transactionService.sendMoney(transactionRequestDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all my transactions
    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getMyTransactions() {

        List<TransactionResponseDto> response =
                transactionService.getMyTransactions();

        return ResponseEntity.ok(response);
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(
            @PathVariable Long id) {

        TransactionResponseDto response =
                transactionService.getTransactionById(id);

        return ResponseEntity.ok(response);
    }

}