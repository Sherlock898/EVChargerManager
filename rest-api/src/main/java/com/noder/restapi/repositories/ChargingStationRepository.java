package com.noder.restapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.noder.restapi.models.ChargingStation;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, Long> {
  @Query("SELECT cs FROM ChargingStation cs JOIN cs.administrators a WHERE a.id = :adminId")
  List<ChargingStation> findByAdministratorId(@Param("adminId") Long adminId);

}