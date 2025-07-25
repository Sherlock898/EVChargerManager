package com.noder.restapi.dtos;

public class ChargerResponseDTO {
    private Long id;
    private String name;
    private String location;
    private String status;
    private Integer connectorCount;
    private String uri;
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getConnectorCount() {
        return connectorCount;
    }

    public void setConnectorCount(Integer connectorCount) {
        this.connectorCount = connectorCount;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
