package com.noder.ocppserver.ocpp.messages;

public record DataTransferConf(DataTransferStatus status, String data) {
    public enum DataTransferStatus {
        Accepted,
        Rejected,
        UnknownMessageId
    }
}
