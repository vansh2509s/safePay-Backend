package com.self.SafePay.Controller;

import com.self.SafePay.Service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QrController {

    private final QrService qrService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateQr() {

        byte[] qr = qrService.generateQr();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qr);
    }
}
