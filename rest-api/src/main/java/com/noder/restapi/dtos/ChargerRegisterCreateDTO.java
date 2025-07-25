package com.noder.restapi.dtos;

public class ChargerRegisterCreateDTO {
    private String name;
    private Long stationId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
}
