package com.noder.restapi.dtos;

import jakarta.validation.constraints.NotNull;

public class ChargingStationCreateDTO {
    @NotNull
    private String location;
    @NotNull
    private String name;
    private String photoUrl;
    private String info;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
}
