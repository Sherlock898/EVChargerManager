package com.noder.ocppserver.ocpp.messages;

public record UnlockConnectorConf(UnlockStatus status) {
    public enum UnlockStatus{
        Unlocked,
        UnlockFailed,
        NotSupported
    };
}