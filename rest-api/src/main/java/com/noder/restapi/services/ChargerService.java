package com.noder.restapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.noder.restapi.dtos.ChargingStationCreateDTO;
import com.noder.restapi.models.Charger;
import com.noder.restapi.models.ChargingStation;
import com.noder.restapi.models.UserEntity;
import com.noder.restapi.repositories.ChargerRepository;
import com.noder.restapi.repositories.ChargingStationRepository;
import com.noder.restapi.repositories.UserRepository;

@Service
public class ChargerService {
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;
    private final ChargingStationRepository chargingStationRepository;

    public ChargerService(UserRepository userRepository, ChargerRepository chargerRepository, ChargingStationRepository chargingStationRepository) {
        this.userRepository = userRepository;
        this.chargerRepository = chargerRepository;
        this.chargingStationRepository = chargingStationRepository;
    }

    public Optional<Charger> saveCharger(Charger charger) {
        return Optional.of(chargerRepository.save(charger));
    }

    public Optional<Charger> getCharger(Long chargerId) {
        return chargerRepository.findById(chargerId);
    }
    
    public List<Charger> getChargersFromUser(Long userId) {
        List<ChargingStation> stations = chargingStationRepository.findByAdministratorId(userId);
        return stations.stream()
            .flatMap(s -> s.getChargers().stream())
            .toList();
    }

    public boolean isRegistered(Long chargerId) {
        return chargerRepository.existsById(chargerId);
    }

    public List<ChargingStation> getStationsFromUser(Long userId) {
        return chargingStationRepository.findByAdministratorId(userId);
    }
    
    public ChargingStation saveStationFromDTO(ChargingStationCreateDTO chargingStationDTO, Long userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setName(chargingStationDTO.getName());
        chargingStation.setLocation(chargingStationDTO.getLocation());
        chargingStation.setPhotoUrl(chargingStationDTO.getPhotoUrl());
        chargingStation.setInfo(chargingStationDTO.getInfo());

        chargingStation.setAdministrators(List.of(user));
        // chargingStation.(List.of(user));
        return chargingStationRepository.save(chargingStation);
    }

    public boolean allowConnection(Long chargerId) {
        if (chargerRepository.existsById(chargerId)) {
            return true;
        } else {
            return false;
        }
    }
}
