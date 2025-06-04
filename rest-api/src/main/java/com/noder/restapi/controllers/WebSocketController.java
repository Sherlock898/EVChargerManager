package com.noder.restapi.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.noder.restapi.dtos.TransactionCreateDTO;
import com.noder.restapi.dtos.TransactionResponseDTO;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RestTemplate restTemplate;

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate, RestTemplate restTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public SimpMessagingTemplate getSimpMessagingTemplate() {
        return simpMessagingTemplate;
    }
    
    @MessageMapping("/createTransaction")
    public void handleCreateTransaction(TransactionCreateDTO transactionCreateDTO) {
        String apiUrl = "http://localhost:8080/api/v1/transactions";
        TransactionResponseDTO response = restTemplate.postForObject(apiUrl, transactionCreateDTO, TransactionResponseDTO.class);

        if (response != null) {
            simpMessagingTemplate.convertAndSend("/topic/transactionResponse", response);
        } else {
            simpMessagingTemplate.convertAndSend("/topic/transactionResponse", "Error creating transaction.");
        }
    }
}
