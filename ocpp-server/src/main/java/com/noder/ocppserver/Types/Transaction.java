package com.noder.ocppserver.Types;

import java.time.Instant;

/**
 * Transaction class to represent a transaction
 * Transaction ids sent to a charger must be unique in the scope of the charging station
 */
public class Transaction {

    public enum TransactionStatus{
        Active,
        Ended
    };

    private static Integer transactionCounter = 0;  // Placeholder to generate unique transaction ids, should be replaced with a proper id generator or database

    private Integer transactionId;
    private int connectorId;
    private Instant startDate;
    private Instant endDate;
    private Integer meterStart;
    private Integer meterStop;
    private TransactionStatus status;

    public Transaction(int connectorId, Integer meterStart, Instant startDate){
        this.transactionId = transactionCounter++;
        this.connectorId = connectorId;
        this.startDate = startDate;
        this.meterStart = meterStart;
        this.meterStop = null;
        this.endDate = null;
        this.status = TransactionStatus.Active;
    }

    public void endTransaction(Integer meterStop){
        this.meterStop = meterStop;
        this.endDate = Instant.now();
        this.status = TransactionStatus.Ended;
    }


    public Integer getTransactionId() {
        return transactionId;
    }

    public TransactionStatus getTransactionStatus() {
        return status;
    }
}