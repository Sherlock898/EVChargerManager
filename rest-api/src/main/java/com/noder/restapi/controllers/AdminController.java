package com.noder.restapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noder.restapi.dtos.ChargingStationCreateDTO;
import com.noder.restapi.models.Charger;
import com.noder.restapi.models.ChargingStation;
import com.noder.restapi.models.Transaction;
import com.noder.restapi.models.UserEntity;
import com.noder.restapi.services.ChargerService;
import com.noder.restapi.services.TransactionService;
import com.noder.restapi.services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final ChargerService chargerService;
    private final TransactionService transactionService;

    public AdminController(UserService userService, ChargerService chargerService, TransactionService transactionService) {
        this.userService = userService;
        this.chargerService = chargerService;
        this.transactionService = transactionService;
    }

    // Get all chargers belonging to a specific admin
    @GetMapping("/chargers")
    public ResponseEntity<List<Charger>> getChargers() {
        Long userId = userService.getUserIdFromAuthentication().orElse(null);
        if (userId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(chargerService.getChargersFromUser(userId));
    }

    // Register a new charger
    @PostMapping("/chargers")
    public ResponseEntity<Charger> registerCharger(Charger charger) {
        // TODO: Set id
        UserEntity user = userService.getUserFromAuthentication().orElse(null);
        if (user == null) return ResponseEntity.badRequest().build();
        charger.setAdministrator(user);
        return ResponseEntity.ok(chargerService.saveCharger(charger).get());
    }

    // Get information about specific charger
    @GetMapping("/chargers/{chargerId}")
    public ResponseEntity<Charger> getCharger(@PathVariable Long chargerId) {
        return chargerService.getCharger(chargerId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());      
    }
    
    // TODO: Use transactions DTO
    // Get all transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions() {
        Long userId = userService.getUserIdFromAuthentication().orElse(null);
        if (userId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(transactionService.getTransactionsFromUserId(userId));
    }

    // Get information about specific transaction
    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // List of all stations for a specific admin
    @GetMapping("/stations")
    public ResponseEntity<List<ChargingStation>> getStations() {
        Long userId = userService.getUserIdFromAuthentication().orElse(null);
        if (userId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(chargerService.getStationsFromUser(userId));
    }

    // TODO: Use stations DTO (if needed)
    @PostMapping("/stations")
    public ResponseEntity<ChargingStation> postMethodName(@Valid @RequestBody ChargingStationCreateDTO chargingStation) {
        Long userId = userService.getUserIdFromAuthentication().orElse(null);
        if (userId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(chargerService.saveStationFromDTO(chargingStation, userId));
    }
        
}
