package com.noder.restapi.dtos;

public class ChargingStationResponseDTO {
    private Long id;
    private String location;
    private String name;
    private String photoUrl;
    private String info;

    public ChargingStationResponseDTO(Long id, String location, String name, String photoUrl, String info){
        this.id = id;
        this.location = location;
        this.name = name;
        this.photoUrl = photoUrl;
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getInfo() {
        return info;
    }
}
