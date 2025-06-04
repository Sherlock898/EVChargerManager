package com.noder.ocppserver.ocpp.messages;

public record ChangeConfigurationConf(Status  status) {
    public enum Status {
        Accepted,
        Rejected,
        RebootRequired,
        NotSupported
    }
}
