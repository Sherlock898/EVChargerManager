package com.noder.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noder.restapi.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    // Fin all transactions linked to a userId
    // TODO: Test this, idk if this works
    
}
