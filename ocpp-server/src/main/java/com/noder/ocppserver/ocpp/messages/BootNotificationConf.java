package com.noder.ocppserver.ocpp.messages;
import java.time.Instant;

public record BootNotificationConf(Instant currentTime, Integer interval, Status status) {
    public enum Status {
        Accepted,
        Pending,
        Rejected
    }
    
}