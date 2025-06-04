package com.noder.ocppserver.ocpp.messages;

public record ResetConf(ResetStatus status) {
    public enum ResetStatus{
        Accepted,
        Rejected
    };
}
