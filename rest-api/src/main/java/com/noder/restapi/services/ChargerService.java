package com.noder.restapi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.noder.restapi.dtos.ChargerRegisterCreateDTO;
import com.noder.restapi.dtos.ChargerResponseDTO;
import com.noder.restapi.dtos.ChargingStationCreateDTO;
import com.noder.restapi.dtos.ChargingStationResponseDTO;
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

    public List<ChargingStationResponseDTO> getStationsFromUser(Long userId) {
        return chargingStationRepository.findByAdministratorId(userId).stream()
            .map(station -> new ChargingStationResponseDTO(
                station.getId(),
                station.getLocation(),
                station.getName(),
                station.getPhotoUrl(),
                station.getInfo()))
            .toList();
    }

    public ChargingStationResponseDTO saveStationFromDTO(ChargingStationCreateDTO chargingStationDTO, Long userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setName(chargingStationDTO.getName());
        chargingStation.setLocation(chargingStationDTO.getLocation());
        chargingStation.setPhotoUrl(chargingStationDTO.getPhotoUrl());
        chargingStation.setInfo(chargingStationDTO.getInfo());

        chargingStation.setAdministrators(List.of(user));
        // chargingStation.(List.of(user));
        System.out.println("Saving charging station: " + chargingStation);
        chargingStationRepository.save(chargingStation);
        System.out.println("Saved charging station: " + chargingStation);
        ChargingStationResponseDTO responseDTO = new ChargingStationResponseDTO(chargingStation.getId(), chargingStation.getLocation(), chargingStation.getName(), chargingStation.getPhotoUrl(), chargingStation.getInfo());
        return responseDTO;
    }

    public boolean allowConnection(Long chargerId) {
        if (chargerRepository.existsById(chargerId)) {
            return true;
        } else {
            return false;
        }
    }

    public ChargerResponseDTO registerCharger(ChargerRegisterCreateDTO chargerCreateDTO, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ChargingStation chargingStation = chargingStationRepository.findById(chargerCreateDTO.getStationId())
            .orElseThrow(() -> new RuntimeException("Charging station not found"));
        Charger charger = new Charger();
        charger.setName(chargerCreateDTO.getName());
        charger.setConector_count(-1); // -1 means unknown
        charger.setUri("URL_TO_BE_SET"); // Placeholder, should be set later
        charger.setStatus(Charger.ChargerStatus.WAITING_FOR_SETUP);
        charger.setWebSocketServer(null); // Placeholder, should be set later
        charger.setChargingStation(chargingStation);
        String chargerKey;
        do {
            // Shorter UUID because user have to put this on his charger so is more convenient.
            chargerKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
        } while (chargerRepository.existsByKey(chargerKey));
        charger.setKey(chargerKey);
        chargerRepository.save(charger);

        ChargerResponseDTO responseDTO = new ChargerResponseDTO();
        responseDTO.setId(charger.getId());
        responseDTO.setName(charger.getName());
        responseDTO.setLocation(chargingStation.getLocation());
        responseDTO.setStatus(charger.getStatus().name());
        responseDTO.setConnectorCount(charger.getConnector_count());
        responseDTO.setUri(charger.getUri());
        responseDTO.setKey(charger.getKey());
        System.out.println("Registered charger: " + responseDTO);
        return responseDTO;
    }

    public List<ChargerResponseDTO> getChargersByStationId(Long stationId) {
        ChargingStation chargingStation = chargingStationRepository.findById(stationId)
            .orElseThrow(() -> new RuntimeException("Charging station not found"));
        
        return chargerRepository.findByChargingStationId(stationId).stream()
            .map(charger -> {
                ChargerResponseDTO dto = new ChargerResponseDTO();
                dto.setId(charger.getId());
                dto.setName(charger.getName());
                dto.setLocation(chargingStation.getLocation());
                dto.setStatus(charger.getStatus().name());
                dto.setConnectorCount(charger.getConnector_count());
                dto.setUri(charger.getUri());
                dto.setKey(charger.getKey());
                return dto;
            })
            .toList();
    }
}
