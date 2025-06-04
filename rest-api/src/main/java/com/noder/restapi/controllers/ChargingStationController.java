package com.noder.restapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noder.restapi.models.ChargingStation;
import com.noder.restapi.repositories.ChargingStationRepository;

@RestController
@RequestMapping("/api/v1/charging-stations")
public class ChargingStationController {

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @GetMapping
    public List<ChargingStation> getAllChargingStations() {
        return chargingStationRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<ChargingStation> createchargingStation(@RequestBody ChargingStation chargingStation) {
        if (chargingStation.getName() == null || chargingStation.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        ChargingStation savedChargingStation = chargingStationRepository.save(chargingStation);
        return ResponseEntity.ok(savedChargingStation);
    }
}
