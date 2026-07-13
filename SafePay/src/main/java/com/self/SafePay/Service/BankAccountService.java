package com.self.SafePay.Service;

import com.self.SafePay.Entity.BankAccount;
import com.self.SafePay.Entity.User;
import com.self.SafePay.Repository.BankRepository;
import com.self.SafePay.Repository.UserRepository;
import com.self.SafePay.Security.CustomUserDetails;
import com.self.SafePay.dto.BankAccountRequestDto;
import com.self.SafePay.dto.BankAccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService
{
    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return customUserDetails.getUser();
    }

    public BankAccountResponseDto addBankAccount(BankAccountRequestDto bankAccountRequestDto) {

        User loggedInUser = getLoggedInUser();

        BankAccount bankAccount = new BankAccount();

        bankAccount.setAccountHolderName(loggedInUser.getFullName());
        bankAccount.setAccountNumber(bankAccountRequestDto.getAccountNumber());
        bankAccount.setBankName(bankAccountRequestDto.getBankName());
        bankAccount.setIfscCode(bankAccountRequestDto.getIfscCode());
        bankAccount.setBranchName(bankAccountRequestDto.getBranchName());
        bankAccount.setAccountType(bankAccountRequestDto.getAccountType());

        // Default balance
        bankAccount.setBankBalance(10000.0);

        bankAccount.setUser(loggedInUser);

        BankAccount savedBankAccount = bankRepository.save(bankAccount);

        BankAccountResponseDto response = new BankAccountResponseDto();

        response.setId(savedBankAccount.getId());
        response.setAccountHolderName(savedBankAccount.getAccountHolderName());
        response.setAccountNumber(savedBankAccount.getAccountNumber());
        response.setBankName(savedBankAccount.getBankName());
        response.setBranchName(savedBankAccount.getBranchName());
        response.setIfscCode(savedBankAccount.getIfscCode());
        response.setBalance(savedBankAccount.getBankBalance());
        response.setAccountType(savedBankAccount.getAccountType());
        response.setCreatedAt(savedBankAccount.getCreatedAt());

        return response;
    }

public BankAccountResponseDto updateAccountDetails(Long id,
                                                   BankAccountRequestDto bankAccountRequestDto) {

    User loggedInUser = getLoggedInUser();

    BankAccount bankAccount = bankRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invalid id"));

    if (!bankAccount.getUser().getId().equals(loggedInUser.getId())) {
        throw new RuntimeException("Access Denied");
    }

    bankAccount.setAccountHolderName(loggedInUser.getFullName());
    bankAccount.setAccountNumber(bankAccountRequestDto.getAccountNumber());
    bankAccount.setBankName(bankAccountRequestDto.getBankName());
    bankAccount.setIfscCode(bankAccountRequestDto.getIfscCode());
    bankAccount.setBranchName(bankAccountRequestDto.getBranchName());
    bankAccount.setAccountType(bankAccountRequestDto.getAccountType());

    // Don't update bank balance here

    BankAccount savedBankAccount = bankRepository.save(bankAccount);

    BankAccountResponseDto response = new BankAccountResponseDto();

    response.setId(savedBankAccount.getId());
    response.setAccountHolderName(savedBankAccount.getAccountHolderName());
    response.setAccountNumber(savedBankAccount.getAccountNumber());
    response.setBankName(savedBankAccount.getBankName());
    response.setBranchName(savedBankAccount.getBranchName());
    response.setIfscCode(savedBankAccount.getIfscCode());
    response.setBalance(savedBankAccount.getBankBalance());
    response.setAccountType(savedBankAccount.getAccountType());
    response.setCreatedAt(savedBankAccount.getCreatedAt());

    return response;
}
    public void deleteBankAccountById(Long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User loggedInUser = customUserDetails.getUser();
        BankAccount bankAccount= bankRepository.findById(id).
                orElseThrow(()->new RuntimeException("Invalid id"));
        if(!bankAccount.getUser().getId().equals(loggedInUser.getId()))
        {
            throw new RuntimeException("Access Denied");
        }
        bankRepository.deleteById(id);
    }
}
