package com.noder.restapi.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Transaction {
    public enum TransactionStatus {
        ACTIVE,
        COMPLETED,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant start_time;
    private Instant end_time;
    private String start_meter_value;
    private String end_meter_value;
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "connector_id", nullable = false)
    private Connector connector;

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

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
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
    
    public String getEnd_meter_value() {
        return end_meter_value;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Connector getConnector() {
        return connector;
    }

    @Column(updatable = false)
    private Instant created_at;
    private Instant updated_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = Instant.now();
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }
}