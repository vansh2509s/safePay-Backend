package com.self.SafePay.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="beneficiaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    @Column(nullable = false)
    private String benificaryName;
    @Column(nullable = false)
    private String upiId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
