package com.self.SafePay.Service;

import com.self.SafePay.Entity.BankAccount;
import com.self.SafePay.Entity.Transaction;
import com.self.SafePay.Entity.User;
import com.self.SafePay.Entity.Wallet;
import com.self.SafePay.Enum.TransactionStatus;
import com.self.SafePay.Enum.TransactionType;
import com.self.SafePay.Repository.BankRepository;
import com.self.SafePay.Repository.TransactionRepository;
import com.self.SafePay.Repository.UserRepository;
import com.self.SafePay.Repository.WalletRepository;
import com.self.SafePay.Security.CustomUserDetails;
import com.self.SafePay.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService
{

        private final WalletRepository walletRepository;
        private final UserRepository userRepository;
        private final BankRepository bankRepository;
        private final TransactionRepository transactionRepository;
        private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return customUserDetails.getUser();
    }

        public WalletResponseDto getWallet(String email)
        {
            Wallet wallet = walletRepository.findByUserEmail(email).
                    orElseThrow(()->new RuntimeException("Wallet not found"));
            WalletResponseDto walletResponseDto = new WalletResponseDto();
            walletResponseDto.setId(wallet.getId());
            walletResponseDto.setBalance(wallet.getBalance());
            walletResponseDto.setCreatedAt(wallet.getCreatedAt());
            walletResponseDto.setUpdatedAt(wallet.getUpdatedAt());
            return walletResponseDto;
        }
        public BalanceResponseDto getBalance(String email)
        {
            Wallet wallet= walletRepository.findByUserEmail(email).
                    orElseThrow(()->new RuntimeException("Wallet not found"));
            BalanceResponseDto balanceResponseDto = new BalanceResponseDto();
            balanceResponseDto.setBalance(wallet.getBalance());
            return balanceResponseDto;
        }
      public DepositResponseDto depositMoney(DepositRequestDto depositRequestDto)
      {
          User loggedIn= getLoggedInUser();
          BankAccount bankAccount = bankRepository.findById(depositRequestDto.getBankAccountId())
                  .orElseThrow(() -> new RuntimeException("Bank Account Not Found"));
          if (!bankAccount.getUser().getId().equals(loggedIn.getId())) {
              throw new RuntimeException("Access Denied");
          }
          if (depositRequestDto.getAmount() <= 0) {
              throw new RuntimeException("Amount must be greater than 0");
          }
          Wallet wallet = walletRepository.findByUser(loggedIn)
                  .orElseThrow(() -> new RuntimeException("Wallet not found"));
          wallet.setBalance(wallet.getBalance() + depositRequestDto.getAmount());
          walletRepository.save(wallet);
          Transaction transaction = new Transaction();
          transaction.setReceiver(loggedIn);
          transaction.setAmount(depositRequestDto.getAmount());
          transaction.setRemark(depositRequestDto.getRemark());
          transaction.setTransactionStatus(TransactionStatus.SUCCESS);
          transaction.setTransactionType(TransactionType.DEPOSIT);
          Transaction savedTransaction=transactionRepository.save(transaction);
          DepositResponseDto response = new DepositResponseDto();

          response.setTransactionId(savedTransaction.getId());
          response.setAmount(savedTransaction.getAmount());
          response.setUpdatedBalance(wallet.getBalance());
          response.setTransactionReference(savedTransaction.getTransactionReference());
          response.setCreatedAt(savedTransaction.getCreatedAt());
          return response;
      }
    @Transactional
    public WithdrawResponseDto withdrawMoney(WithdrawRequestDto withdrawRequestDto)
    {
        User loggedIn = getLoggedInUser();

        BankAccount bankAccount = bankRepository
                .findById(withdrawRequestDto.getBankAccountId())
                .orElseThrow(() -> new RuntimeException("Bank Account Not Found"));

        if (!bankAccount.getUser().getId().equals(loggedIn.getId()))
        {
            throw new RuntimeException("Access Denied");
        }

        if (withdrawRequestDto.getAmount() <= 0)
        {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Wallet wallet = walletRepository.findByUser(loggedIn)
                .orElseThrow(() -> new RuntimeException("Wallet Not Found"));

        if (wallet.getBalance() < withdrawRequestDto.getAmount())
        {
            throw new RuntimeException("Insufficient Wallet Balance");
        }

        wallet.setBalance(wallet.getBalance() - withdrawRequestDto.getAmount());

        walletRepository.save(wallet);

        Transaction transaction = new Transaction();

        transaction.setSender(loggedIn);
        transaction.setReceiver(null);
        transaction.setAmount(withdrawRequestDto.getAmount());
        transaction.setRemark("Money Withdrawn");
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionType(TransactionType.WITHDRAW);

        Transaction savedTransaction = transactionRepository.save(transaction);

        WithdrawResponseDto response = new WithdrawResponseDto();

        response.setTransactionId(savedTransaction.getId());
        response.setAmount(savedTransaction.getAmount());
        response.setRemainingBalance(wallet.getBalance());
        response.setTransactionReference(savedTransaction.getTransactionReference());
        response.setTransactionStatus(savedTransaction.getTransactionStatus());
        response.setCreatedAt(savedTransaction.getCreatedAt());

        return response;
    }

}
