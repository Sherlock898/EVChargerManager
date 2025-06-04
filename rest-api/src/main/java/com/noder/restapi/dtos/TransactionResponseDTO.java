package com.noder.restapi.dtos;

import java.time.Instant;

import com.noder.restapi.models.Transaction;

public class TransactionResponseDTO {
    private Long id;
    private Instant start_time;
    private Instant end_time;
    private String start_meter_value;
    private String end_meter_value;
    private String status;

    public TransactionResponseDTO(Long id, Instant start_time, Instant end_time, String start_meter_value, String end_meter_value, String status) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_meter_value = start_meter_value;
        this.end_meter_value = end_meter_value;
        this.status = status;
    }

    public TransactionResponseDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.start_time = transaction.getStart_time();
        this.end_time = transaction.getEnd_time();
        this.start_meter_value = transaction.getStart_meter_value();
        this.end_meter_value = transaction.getEnd_meter_value();
        this.status = transaction.getStatus().name();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStart_time(Instant start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Instant end_time) {
        this.end_time = end_time;
    }

    public void setStart_meter_value(String start_meter_value) {
        this.start_meter_value = start_meter_value;
    }

    public void setEnd_meter_value(String end_meter_value) {
        this.end_meter_value = end_meter_value;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Instant getStart_time() {
        return start_time;
    }

    public Instant getEnd_time() {
        return end_time;
    }

    public String getStart_meter_value() {
        return start_meter_value;
    }

    public String setEnd_meter_value() {
        return end_meter_value;
    }

    public String setStatus() {
        return status;
    }
}
