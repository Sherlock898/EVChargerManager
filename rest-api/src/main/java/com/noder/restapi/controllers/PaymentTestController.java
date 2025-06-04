package com.noder.restapi.controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.annotations.JsonAdapter;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;

@RestController
@RequestMapping("/test-api/v1/payment")
public class PaymentTestController {
    
    // Simualtes an end of transaction and payment
    @PostMapping("/pay")
    public ResponseEntity<String> pay() throws TransactionCreateException, IOException {
        WebpayPlus.Transaction tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
        final WebpayPlusTransactionCreateResponse response = tx.create("1", "sessionId", 1500, "https://goldenelectric.cl/");
        String responseUrl = response.getUrl();
        String responseToken = response.getToken();
        return ResponseEntity.ok("{\"url\": \"" + responseUrl + "\", \"token\": \"" + responseToken + "\"}");
    }
}
