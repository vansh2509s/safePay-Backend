package com.self.SafePay.Entity;

import com.self.SafePay.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Username is required")
    @Column(nullable = false)
    private String fullName;
    @NotBlank(message="Email is Required")
    @Email(message="Enter a valid email")
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank(message="Phone number is required")
    @Pattern(regexp = "[0-9]{10}$",message="Phone number must be of 10 digits")
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @NotBlank(message="Password Required")
    @Size(min=8, message="Password must be atleast of 8 characters")
    @Column(nullable=false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Boolean isActive= true;
    private Boolean isVerified = false;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Wallet wallet;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts = new ArrayList<>();
    @Column(unique = true, nullable = false)
    private String upiId;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Beneficiary> beneficiaries = new ArrayList<>();


}
