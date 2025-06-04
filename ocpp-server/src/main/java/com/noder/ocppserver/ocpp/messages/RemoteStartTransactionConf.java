package com.noder.ocppserver.ocpp.messages;

public record RemoteStartTransactionConf(RemoteStartStopStatus status) {
    public enum RemoteStartStopStatus{
        Accepted,
        Rejected
    };
}
