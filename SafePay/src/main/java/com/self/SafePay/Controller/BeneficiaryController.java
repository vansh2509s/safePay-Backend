package com.self.SafePay.Controller;
import com.self.SafePay.Service.BeneficiaryService;
import com.self.SafePay.dto.BeneficiaryRequestDto;
import com.self.SafePay.dto.BeneficiaryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController
{

    private final BeneficiaryService beneficiaryService;
    @PostMapping
    public ResponseEntity<?> addBeneficiary(@RequestBody BeneficiaryRequestDto beneficiaryRequestDto)
    {
        BeneficiaryResponseDto beneficiary = beneficiaryService
                .addBeneficiary(beneficiaryRequestDto);
        return new  ResponseEntity<>(beneficiary, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBeneficiaryById(@PathVariable Long id)
    {
        BeneficiaryResponseDto response = beneficiaryService.getBeneficiaryById(id);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeneficiaryById(@PathVariable Long id, @RequestBody BeneficiaryRequestDto beneficiaryRequestDto)
    {
        BeneficiaryResponseDto response = beneficiaryService.updateBeneficiary(id, beneficiaryRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBeneficiaryById(@PathVariable Long id)
    {
        beneficiaryService.deleteBeneficiary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<BeneficiaryResponseDto>> getAllBeneficiary()
    {
        List<BeneficiaryResponseDto> beneficiaryResponseDto =  beneficiaryService.getAllBeneficiary();
        return new  ResponseEntity<>(beneficiaryResponseDto, HttpStatus.OK);
    }
}
