package com.noder.restapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noder.restapi.dtos.ChargerConnectionRequestDTO;
import com.noder.restapi.models.Transaction;
import com.noder.restapi.services.ChargerService;
import com.noder.restapi.services.TransactionService;
import com.noder.restapi.services.UserService;

// Controller for all ocpp server related operations
@RestController
@RequestMapping("/api/v1/chargers")
public class ChargerController {

    private final ChargerService chargerService;
    private final TransactionService transactionService;
    private final UserService userService;

    public ChargerController(ChargerService chargerService, TransactionService transactionService, UserService userService) {
        this.chargerService = chargerService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    // Request for connect charger
    public ResponseEntity<String> requestChargeConnection(ChargerConnectionRequestDTO chargerConnectionRequest) {
        Long chargerId = chargerConnectionRequest.getChargerId();
        if(chargerService.allowConnection(chargerId)){
            return ResponseEntity.ok("Connection allowed");
        }else{
            return ResponseEntity.status(403).body("Connection not allowed");
        }
    }    
}
