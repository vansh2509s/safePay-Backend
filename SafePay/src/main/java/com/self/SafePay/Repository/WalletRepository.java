package com.self.SafePay.Repository;

import com.self.SafePay.Entity.User;
import com.self.SafePay.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long>
{
    Optional<Wallet> findByUserEmail(String email);
    Optional<Wallet> findByUser(User user);
}
