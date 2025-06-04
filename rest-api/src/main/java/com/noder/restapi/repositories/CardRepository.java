package com.noder.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noder.restapi.models.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
}
