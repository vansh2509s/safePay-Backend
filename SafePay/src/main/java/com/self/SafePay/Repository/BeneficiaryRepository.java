package com.self.SafePay.Repository;

import com.self.SafePay.Entity.Beneficiary;
import com.self.SafePay.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long>
{
    Optional<Beneficiary> findByIdAndUser(Long id, User user);
    boolean existsByUserAndUpiId(User user, String upiId);
}
