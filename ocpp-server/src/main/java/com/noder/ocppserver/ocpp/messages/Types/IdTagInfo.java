package com.noder.ocppserver.ocpp.messages.Types;

import java.time.Instant;

public record IdTagInfo(Instant expiryDate, String parentIdTag, AuthorizationStatus status) {

    public enum AuthorizationStatus{
        Accepted,
        Blocked,
        Expired,
        Invalid,
        ConcurrentTx
    };
}
