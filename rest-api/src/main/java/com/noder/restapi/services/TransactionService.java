package com.noder.restapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.noder.restapi.dtos.TransactionCreateDTO;
import com.noder.restapi.models.Transaction;
import com.noder.restapi.models.Transaction.TransactionStatus;
import com.noder.restapi.repositories.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(long id) {
        return transactionRepository.findById(id);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> updateTransaction(Long id, TransactionCreateDTO transactionCreateDTO) {
        return transactionRepository.findById(id).map(existingTransaction -> {
            existingTransaction.setStart_time(transactionCreateDTO.getStart_time());
            existingTransaction.setStart_meter_value(transactionCreateDTO.getStart_meter_value());
            existingTransaction.setConnector(transactionCreateDTO.getConnector());
            return transactionRepository.save(existingTransaction);
        });
    }

    public boolean deleteTransaction(Long id) {
        if(transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Transaction> updateTransactionStatus(Long id, TransactionStatus status) {
        return transactionRepository.findById(id).map(transaction -> {
            transaction.setStatus(status);
            return transactionRepository.save(transaction);
        });
    }

    public List<Transaction> getTransactionsFromUserId(Long userId) {
        return transactionRepository.findByConnectorChargerAdministrator_id(userId);

    }
}
