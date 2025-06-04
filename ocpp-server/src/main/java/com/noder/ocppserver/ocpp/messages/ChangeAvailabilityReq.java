package com.noder.ocppserver.ocpp.messages;


public record ChangeAvailabilityReq(Integer connectorId, AvailabilityType type) {
    public enum AvailabilityType {
        Inoperative,
        Operative
    }
}

