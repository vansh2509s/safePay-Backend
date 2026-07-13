package com.self.SafePay.Repository;

import com.self.SafePay.Entity.Beneficiary;
import com.self.SafePay.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>
{
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByUpiId(String upiId);
    Optional<User> findByUpiId(String upiId);

}
