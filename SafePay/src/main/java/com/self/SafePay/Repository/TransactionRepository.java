package com.self.SafePay.Repository;

import com.self.SafePay.Entity.Transaction;
import com.self.SafePay.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        Optional<Transaction> findByTransactionReference(String transactionReference);

        List<Transaction> findBySender(User sender);

        List<Transaction> findByReceiver(User receiver);

        Optional<Transaction> findByIdAndSender(Long id, User sender);

         boolean existsByTransactionReference(String transactionReference);
    }

