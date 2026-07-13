    package com.self.SafePay.Service;

    import com.self.SafePay.Entity.Beneficiary;
    import com.self.SafePay.Entity.Transaction;
    import com.self.SafePay.Entity.User;
    import com.self.SafePay.Entity.Wallet;
    import com.self.SafePay.Repository.BeneficiaryRepository;
    import com.self.SafePay.Repository.TransactionRepository;
    import com.self.SafePay.Repository.UserRepository;
    import com.self.SafePay.Repository.WalletRepository;
    import com.self.SafePay.Security.CustomUserDetails;
    import com.self.SafePay.Enum.TransactionStatus;
    import com.self.SafePay.Enum.TransactionType;
    import com.self.SafePay.dto.TransactionRequestDto;
    import com.self.SafePay.dto.TransactionResponseDto;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;
    import java.security.SecureRandom;
    import java.util.ArrayList;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class TransactionService
    {
        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;
        private final WalletRepository  walletRepository;
        private final BeneficiaryRepository beneficiaryRepository;
        private final SecureRandom secureRandom = new SecureRandom();

        private User getloggedInUser()
        {
            Authentication authentication = SecurityContextHolder
                    .getContext()
                    .getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return customUserDetails.getUser();
        }
        private String generateTransactionReference()
        {

            String reference;

            do {
                long randomNumber = Math.abs(secureRandom.nextLong());
                reference = "SP" + String.valueOf(randomNumber).substring(0, 12);
            } while (transactionRepository.existsByTransactionReference(reference));

            return reference;
        }
        @Transactional
        public TransactionResponseDto sendMoney(TransactionRequestDto transactionRequestDto)
        {
            User loggedIn = getloggedInUser();
            Beneficiary beneficiary = beneficiaryRepository.findById(transactionRequestDto.
                            getBeneficiaryId())
                    .orElseThrow(()->new RuntimeException("No user found with this beneficiary Id"));
            System.out.println("Beneficiary Owner ID = " + beneficiary.getUser().getId());
            System.out.println("Logged In User ID = " + loggedIn.getId());
            if (!beneficiary.getUser().getId().equals(loggedIn.getId()))
            {
                throw new RuntimeException("Access Denied,You are not Allowed to send money to this user");
            }

            String receiverUpiId = beneficiary.getUpiId();
            User receiver =userRepository.findByUpiId(receiverUpiId)
                    .orElseThrow(()->new RuntimeException("No receiver found with this upi id"));
            Wallet senderWallet=walletRepository.findByUser(loggedIn).
                    orElseThrow(()->new RuntimeException("No wallet found"));
            Wallet receiverWallet=walletRepository.findByUser(receiver).
                    orElseThrow(()->new RuntimeException("No wallet found"));
           Transaction transaction = new Transaction();
           transaction.setAmount(transactionRequestDto.getAmount());
            if(!(transaction.getAmount() >0))
            {
                throw new RuntimeException("Amount must be greater than 0");
            }
            if(receiver.equals(loggedIn))
            {
                throw new RuntimeException("Cannot send money to yourself");
            }
            if(senderWallet.getBalance()<transaction.getAmount())
            {
                throw new RuntimeException("Low Balance,Unable to Send Money");
            }
            senderWallet.setBalance(senderWallet.getBalance()-transaction.getAmount());
            receiverWallet.setBalance(receiverWallet.getBalance()+transaction.getAmount());
            walletRepository.save(senderWallet);
            walletRepository.save(receiverWallet);
            transaction.setSender(loggedIn);
            transaction.setReceiver(receiver);
            transaction.setRemark(transactionRequestDto.getRemark());
            transaction.setTransactionReference(generateTransactionReference());
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transaction.setTransactionType(TransactionType.SEND);
            Transaction savedTransaction = transactionRepository.save(transaction);
            TransactionResponseDto response = new TransactionResponseDto();
            response.setId(savedTransaction.getId());
            response.setAmount(savedTransaction.getAmount());
            response.setRemark(savedTransaction.getRemark());
            response.setTransactionReference(savedTransaction.getTransactionReference());
            response.setTransactionStatus(savedTransaction.getTransactionStatus());
            response.setTransactionType(savedTransaction.getTransactionType());
            response.setCreatedAt(savedTransaction.getCreatedAt());
            response.setSenderName(savedTransaction.getSender().getFullName());
            response.setReceiverName(savedTransaction.getReceiver().getFullName());
            response.setReceiverUpiId(savedTransaction.getReceiver().getUpiId());
            return response;
        }
        public List<TransactionResponseDto> getMyTransactions()
        {
            User loggedIn = getloggedInUser();
            List<TransactionResponseDto> transactionResponseDtos = new ArrayList<>();
            for(Transaction transactions:transactionRepository.findBySender(loggedIn))
            {
                TransactionResponseDto response = new TransactionResponseDto();
                response.setId(transactions.getId());
                response.setAmount(transactions.getAmount());
                response.setRemark(transactions.getRemark());
                response.setTransactionReference(transactions.getTransactionReference());
                response.setTransactionStatus(transactions.getTransactionStatus());
                response.setTransactionType(transactions.getTransactionType());
                response.setCreatedAt(transactions.getCreatedAt());
                response.setSenderName(transactions.getSender().getFullName());
                response.setReceiverUpiId(transactions.getReceiver().getUpiId());
                response.setCreatedAt(transactions.getCreatedAt());
                response.setReceiverName(transactions.getReceiver().getFullName());
                transactionResponseDtos.add(response);
            }
            return transactionResponseDtos;
        }
        public TransactionResponseDto getTransactionById(Long id)
        {
            User loggedIn = getloggedInUser();
            Transaction transactions = transactionRepository.findById(id).orElseThrow(()->new RuntimeException("No Transaction found by this id"));
            if (!transactions.getSender().equals(loggedIn)
                    && !transactions.getReceiver().equals(loggedIn)) {
                throw new RuntimeException("Access Denied");
            }
            TransactionResponseDto response = new TransactionResponseDto();
            response.setId(transactions.getId());
            response.setAmount(transactions.getAmount());
            response.setRemark(transactions.getRemark());
            response.setTransactionReference(transactions.getTransactionReference());
            response.setTransactionStatus(transactions.getTransactionStatus());
            response.setTransactionType(transactions.getTransactionType());
            response.setCreatedAt(transactions.getCreatedAt());
            response.setSenderName(transactions.getSender().getFullName());
            response.setReceiverUpiId(transactions.getReceiver().getUpiId());
            response.setCreatedAt(transactions.getCreatedAt());
            response.setReceiverName(transactions.getReceiver().getFullName());
            return response;
        }

    }

