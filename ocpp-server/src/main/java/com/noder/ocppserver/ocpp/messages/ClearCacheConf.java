package com.noder.ocppserver.ocpp.messages;

public record ClearCacheConf(Status status) {
    public enum Status {
        Accepted,
        Rejected
    }
}
