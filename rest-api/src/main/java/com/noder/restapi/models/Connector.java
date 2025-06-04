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
import jakarta.validation.constraints.NotNull;

@Entity
public class Connector {
    public enum ConnectorStatus {
        AVAILABLE,
        PREPARING,
        CHARGING,
        SUSPENDEDEVSE,
        SUSPENDEDEV,
        RESERVED,
        UNAVAILABLE,
        FAULTED
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long connector_id;
    @NotNull
    private ConnectorStatus status;

    @ManyToOne
    @JoinColumn(name = "charger_id", nullable = false)
    private Charger charger;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConnector_id() {
        return connector_id;
    }

    public void setConnector_id(Long connector_id) {
        this.connector_id = connector_id;
    }

    public ConnectorStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectorStatus status) {
        this.status = status;
    }

    public Charger getCharger() {
        return charger;
    }

    public void setCharger(Charger charger) {
        this.charger = charger;
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