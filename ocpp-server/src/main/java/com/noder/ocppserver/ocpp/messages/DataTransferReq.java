package com.noder.ocppserver.ocpp.messages;

public record DataTransferReq(String vendorId, String messageId, String data) {}
