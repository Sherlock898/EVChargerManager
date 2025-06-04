package com.noder.ocppserver.ocpp.messages;

public record ReserveNowConf(ReservationStatus status) {
    public enum ReservationStatus{
        Accepted,
        Faulted,
        Occupied,
        Rejected,
        Unavailable
    };
}
