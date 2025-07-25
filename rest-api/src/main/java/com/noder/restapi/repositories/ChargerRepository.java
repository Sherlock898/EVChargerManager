package com.noder.restapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noder.restapi.models.Charger;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
    boolean existsByKey(String chargerKey);
    List<Charger> findByChargingStationId(Long stationId);
}
