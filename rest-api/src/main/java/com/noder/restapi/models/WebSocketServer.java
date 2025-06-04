package com.noder.restapi.models;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;

@Entity
public class WebSocketServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String url;

    @OneToMany(mappedBy = "webSocketServer")
    private List<Charger> chargers;
    
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

    public Instant getCreatedAt() {
        return created_at;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setCreatedAt(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Instant updated_at) {
        this.updated_at = updated_at;
    }
}
