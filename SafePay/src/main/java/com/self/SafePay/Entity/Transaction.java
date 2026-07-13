package com.self.SafePay.Entity;

import com.self.SafePay.Enum.TransactionStatus;
import com.self.SafePay.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String remark;
    @Column(unique = true)
    private String transactionReference;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

}
