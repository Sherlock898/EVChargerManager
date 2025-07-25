package com.noder.restapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noder.restapi.models.Charger;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
<<<<<<< HEAD

  boolean existsByKey(String chargerKey);
=======
    boolean existsByKey(String chargerKey);
    List<Charger> findByChargingStationId(Long stationId);
>>>>>>> 2fd685a97defff6e4ce9842c58a69a0503fdfe9d
}
