package com.noder.ocppserver.ocpp.messages;

public record SetChargingProfileConf(ChargingProfileStatus status) {
    public enum ChargingProfileStatus{
        Accepted,
        Rejected,
        NotSupported
    };
}
