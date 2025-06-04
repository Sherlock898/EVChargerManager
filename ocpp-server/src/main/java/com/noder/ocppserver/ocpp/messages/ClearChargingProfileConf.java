package com.noder.ocppserver.ocpp.messages;

public record ClearChargingProfileConf(Status status) {
    public enum Status {
        Accepted,
        Unknown
    }
    
}