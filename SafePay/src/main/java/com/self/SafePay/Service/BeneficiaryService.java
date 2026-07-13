package com.self.SafePay.Service;

import com.self.SafePay.Entity.Beneficiary;
import com.self.SafePay.Entity.User;
import com.self.SafePay.Repository.BeneficiaryRepository;
import com.self.SafePay.Repository.UserRepository;
import com.self.SafePay.Security.CustomUserDetails;
import com.self.SafePay.dto.BeneficiaryRequestDto;
import com.self.SafePay.dto.BeneficiaryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }

    public BeneficiaryResponseDto addBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto) {

        User loggedIn = getLoggedInUser();

        User receiver = userRepository.findByUpiId(beneficiaryRequestDto.getUpiId())
                .orElseThrow(() -> new RuntimeException("Receiver doesn't exist with this UPI ID"));

        if (receiver.getId().equals(loggedIn.getId())) {
            throw new RuntimeException("You cannot add yourself as a beneficiary.");
        }

        if (beneficiaryRepository.existsByUserAndUpiId(loggedIn, beneficiaryRequestDto.getUpiId())) {
            throw new RuntimeException("Beneficiary already exists.");
        }

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBenificaryName(receiver.getFullName());
        beneficiary.setNickName(beneficiaryRequestDto.getNickName());
        beneficiary.setUpiId(receiver.getUpiId());
        beneficiary.setUser(loggedIn);

        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);

        BeneficiaryResponseDto responseDto = new BeneficiaryResponseDto();
        responseDto.setId(savedBeneficiary.getId());
        responseDto.setNickName(savedBeneficiary.getNickName());
        responseDto.setBenificaryName(savedBeneficiary.getBenificaryName());
        responseDto.setUpiId(savedBeneficiary.getUpiId());
        responseDto.setCreatedDate(savedBeneficiary.getCreatedAt());

        return responseDto;
    }
    public BeneficiaryResponseDto getBeneficiaryById(Long id)
    {
        User loggedIn=getLoggedInUser();
        Beneficiary beneficiary = beneficiaryRepository.findByIdAndUser(id,loggedIn)
                .orElseThrow(() -> new RuntimeException("Beneficiary Not Found"));
        BeneficiaryResponseDto response = new BeneficiaryResponseDto();
        response.setId(beneficiary.getId());
        response.setBenificaryName(beneficiary.getBenificaryName());
        response.setNickName(beneficiary.getNickName());
        response.setUpiId(beneficiary.getUpiId());
        response.setCreatedDate(beneficiary.getCreatedAt());
        return response;
    }
    public BeneficiaryResponseDto updateBeneficiary(Long id,BeneficiaryRequestDto beneficiaryRequestDto)
    {
     User loggedIn=getLoggedInUser();
     Beneficiary beneficiary = beneficiaryRepository.findByIdAndUser(id,loggedIn).
             orElseThrow(()->new RuntimeException("Beneficiary does not exist"));
     beneficiary.setNickName(beneficiaryRequestDto.getNickName());
     Beneficiary updatedBeneficiary = beneficiaryRepository.save(beneficiary);
     BeneficiaryResponseDto response = new BeneficiaryResponseDto();
        response.setId(updatedBeneficiary.getId());
        response.setNickName(updatedBeneficiary.getNickName());
        response.setBenificaryName(updatedBeneficiary.getBenificaryName());
        response.setUpiId(updatedBeneficiary.getUpiId());
        response.setCreatedDate(updatedBeneficiary.getCreatedAt());
     return response;
    }
    public void  deleteBeneficiary(Long id)
    {
        User loggedIn=getLoggedInUser();
        Beneficiary beneficiary=beneficiaryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Inavlid Id"));
        if(!beneficiary.getUser().getId().equals(loggedIn.getId()))
            {
            throw new RuntimeException("Beneficiary Not Found");
            }
        beneficiaryRepository.deleteById(id);
    }
    public List<BeneficiaryResponseDto> getAllBeneficiary()
    {
        List<BeneficiaryResponseDto> beneficiaryResponseDtos = new ArrayList<>();
        for(Beneficiary beneficiary:beneficiaryRepository.findAll())
        {
            BeneficiaryResponseDto responseDto = new BeneficiaryResponseDto();
            responseDto.setId(beneficiary.getId());
            responseDto.setNickName(beneficiary.getNickName());
            responseDto.setBenificaryName(beneficiary.getBenificaryName());
            responseDto.setUpiId(beneficiary.getUpiId());
            responseDto.setCreatedDate(beneficiary.getCreatedAt());
            beneficiaryResponseDtos.add(responseDto);
        }
        return beneficiaryResponseDtos;
    }
}