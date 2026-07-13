# SafePay 💳

A secure UPI-inspired digital wallet backend built using **Spring Boot**. SafePay allows users to register, authenticate using JWT, manage wallets and bank accounts, add beneficiaries, and transfer money securely while maintaining complete transaction history.

---

## 🚀 Features

### 👤 User Management
- User Registration
- User Login
- JWT Authentication
- BCrypt Password Encryption
- Role-Based Authorization

### 💰 Wallet Module
- Automatic wallet creation during registration
- Check wallet balance
- Deposit money from bank account to wallet
- Withdraw money from wallet to bank account

### 🏦 Bank Account Module
- Add bank account
- View bank account details
- Update bank account information
- Link bank account with user

### 👥 Beneficiary Module
- Add beneficiary
- Update beneficiary
- View beneficiary list
- Prevent duplicate beneficiaries

### 💸 Transaction Module
- Wallet to Wallet money transfer
- Unique transaction reference generation
- Transaction status tracking
- Transaction history
- Balance validation before transfer

### 🔒 Security
- Spring Security
- JWT Authentication
- Stateless Authentication
- Password Encryption using BCrypt
- Protected REST APIs

---

# 🛠 Tech Stack

| Technology | Used |
|------------|------|
| Java | 21 |
| Spring Boot | 3.x |
| Spring Security | ✅ |
| Spring Data JPA | ✅ |
| Hibernate | ✅ |
| MySQL | ✅ |
| Maven | ✅ |
| JWT | ✅ |
| Lombok | ✅ |
| Postman | API Testing |

---

# 📁 Project Structure

```
src
 ├── controller
 ├── dto
 ├── entity
 ├── repository
 ├── security
 ├── service
 ├── exception
 ├── config
 └── SafePayApplication.java
```

---

# 🗄 Database Entities

- User
- Wallet
- BankAccount
- Beneficiary
- Transaction

### Entity Relationships

```
User
 ├── Wallet (One-to-One)
 ├── BankAccount (One-to-One)
 ├── Beneficiary (One-to-Many)
 └── Transaction (Sender & Receiver)
```

---

# 🔑 Authentication

After login, a JWT token is generated.

Include the token in every protected request.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📌 REST APIs

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | /auth/register |
| POST | /auth/login |

---

## Wallet APIs

| Method | Endpoint |
|---------|----------|
| GET | /wallet/balance |
| POST | /wallet/deposit |
| POST | /wallet/withdraw |

---

## Bank Account APIs

| Method | Endpoint |
|---------|----------|
| POST | /bank/add |
| GET | /bank/details |
| PUT | /bank/update |

---

## Beneficiary APIs

| Method | Endpoint |
|---------|----------|
| POST | /beneficiary/add |
| PUT | /beneficiary/update |
| GET | /beneficiary/all |

---

## Transaction APIs

| Method | Endpoint |
|---------|----------|
| POST | /transaction/transfer |
| GET | /transaction/history |

---

# ⚙️ How to Run

### Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/SafePay.git
```

### Navigate

```bash
cd SafePay
```

### Configure Database

Create MySQL Database

```
safepay
```

Update

```
application.properties
```

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

### Run

```bash
mvn spring-boot:run
```

Application starts on

```
http://localhost:8080
```

---

# 🧪 API Testing

The APIs were tested using **Postman**.

Workflow:

```
Register
      ↓
Login
      ↓
Receive JWT Token
      ↓
Add Bank Account
      ↓
Deposit Money
      ↓
Add Beneficiary
      ↓
Transfer Money
      ↓
View Transaction History
```

---

# 🔒 Security Features

- JWT Authentication
- Password Encryption
- Protected Endpoints
- Stateless Authentication
- Input Validation
- Exception Handling

---

# 📈 Future Enhancements

- Docker Support
- Swagger Documentation
- Redis Caching
- Email Verification
- Pagination & Sorting
- QR Code Payments
- Notification Service
- Unit & Integration Testing
- CI/CD Pipeline
- AWS Deployment

---

# 👨‍💻 Author

**Vansh Saxena**

Java Backend Developer

GitHub:
https://github.com/vansh2509s

---

## ⭐ If you found this project helpful, consider giving it a Star.
