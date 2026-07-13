package com.self.SafePay.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.self.SafePay.Entity.User;
import com.self.SafePay.Security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class QrService {

    public byte[] generateQr() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User loggedIn = customUserDetails.getUser();

        String qrData = String.format(
                "upi=%s&name=%s",
                loggedIn.getUpiId(),
                loggedIn.getFullName()
        );

        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    qrData,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(
                    bitMatrix,
                    "PNG",
                    outputStream
            );

            return outputStream.toByteArray();

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR Code", e);
        }
    }
}