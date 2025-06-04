package com.noder.restapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noder.restapi.dtos.TransactionResponseDTO;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    @PostMapping("/websocket")
    public ResponseEntity<String> handleWebResponseEntity(@RequestBody TransactionResponseDTO transactionResponseDTO) {
        System.out.println("Event received from WebSocket server: " + transactionResponseDTO);
        return ResponseEntity.ok("Event processed successfully");
    }
}
