package com.self.SafePay.Entity;

import com.self.SafePay.Enum.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="BankAccounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String accountHolderName;
    @Column(unique=true,nullable=false)
    private String accountNumber;
    @Column(nullable=false)
    private String bankName;
    @Column(nullable=false)
    private String ifscCode;
    private double bankBalance;
    @Column(nullable=false)
    private String branchName;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
