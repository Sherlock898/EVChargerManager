package com.noder.ocppserver.ocpp.messages;

public record RemoteStopTransactionConf(RemoteStartStopStatus status) {
    public enum RemoteStartStopStatus{
        Accepted,
        Rejected
    };
}
