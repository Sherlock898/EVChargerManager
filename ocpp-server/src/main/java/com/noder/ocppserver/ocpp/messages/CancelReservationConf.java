package com.noder.ocppserver.ocpp.messages;

public record CancelReservationConf(Status status) {
    public enum Status {
        Accepted,
        Rejected
    }
}
