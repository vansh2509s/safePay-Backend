package com.self.SafePay.Repository;

import com.self.SafePay.Entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<BankAccount,Long>
{
        Optional<BankAccount> findUserById(Long id);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
