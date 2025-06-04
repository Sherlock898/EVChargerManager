package com.noder.ocppserver.ocpp.messages;

public record ResetReq(ResetType type) {
    public enum ResetType{
        Hard,
        Soft
    };
}
