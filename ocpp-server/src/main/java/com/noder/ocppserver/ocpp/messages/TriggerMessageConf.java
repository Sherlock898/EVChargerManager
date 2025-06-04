package com.noder.ocppserver.ocpp.messages;

public record TriggerMessageConf(TriggerMessageStatus status) {
    public enum TriggerMessageStatus{
        Accepted,
        Rejected,
        NotImplemented
    };
}
