package com.noder.ocppserver.ocpp.messages;

public record SendLocalListConf(UpdateStatus status) {
    public enum UpdateStatus{
        Accepted,
        Failed,
        NotSupported,
        VersionMismatch
    };
}
