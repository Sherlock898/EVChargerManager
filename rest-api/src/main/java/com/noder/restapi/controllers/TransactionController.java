package com.noder.restapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noder.restapi.dtos.TransactionCreateDTO;
import com.noder.restapi.dtos.TransactionResponseDTO;
import com.noder.restapi.dtos.TransactionStatusUpdateDTO;
import com.noder.restapi.models.Transaction;
import com.noder.restapi.models.Transaction.TransactionStatus;
import com.noder.restapi.services.TransactionService;

// TODO: Delete this class
@Deprecated
@RestController
@RequestMapping("/deprecated/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<TransactionResponseDTO> transactionResponseDTOs = transactions.stream().map(transaction -> new TransactionResponseDTO(
            transaction.getId(),
            transaction.getStart_time(),
            transaction.getEnd_time(),
            transaction.getStart_meter_value(),
            transaction.getEnd_meter_value(),
            transaction.getStatus().name()
        )).toList();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(t -> ResponseEntity.ok(new TransactionResponseDTO(
            t.getId(),
            t.getStart_time(),
            t.getEnd_time(),
            t.getStart_meter_value(),
            t.getEnd_meter_value(),
            t.getStatus().name()
        ))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionCreateDTO transactionCreateDTO) {
        Transaction transaction = new Transaction();
        transaction.setConnector(transactionCreateDTO.getConnector());
        transaction.setStart_time(transactionCreateDTO.getStart_time());
        transaction.setStart_meter_value(transactionCreateDTO.getStart_meter_value());

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO(
            createdTransaction.getId(),
            createdTransaction.getStart_time(),
            createdTransaction.getEnd_time(),
            createdTransaction.getStart_meter_value(),
            createdTransaction.getEnd_meter_value(),
            createdTransaction.getStatus().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionCreateDTO transactionCreateDTO) {
        Optional<Transaction> updatedTransaction = transactionService.updateTransaction(id, transactionCreateDTO);
        return updatedTransaction.map(t -> ResponseEntity.ok(new TransactionResponseDTO(
            t.getId(),
            t.getStart_time(),
            t.getEnd_time(),
            t.getStart_meter_value(),
            t.getEnd_meter_value(),
            t.getStatus().name()
        ))).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);
        if (deleted) {
            return ResponseEntity.ok("Transaction deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TransactionResponseDTO> updateTransactionStatus(@PathVariable Long id, @RequestBody TransactionStatusUpdateDTO transactionStatusUpdateDTO) {
        Optional<Transaction> updatedTransaction = transactionService.updateTransactionStatus(id, TransactionStatus.valueOf(transactionStatusUpdateDTO.getStatus()));
        return updatedTransaction.map(t -> ResponseEntity.ok(new TransactionResponseDTO(
            t.getId(),
            t.getStart_time(),
            t.getEnd_time(),
            t.getStart_meter_value(),
            t.getEnd_meter_value(),
            t.getStatus().name()
        ))).orElse(ResponseEntity.notFound().build());
    }
}
