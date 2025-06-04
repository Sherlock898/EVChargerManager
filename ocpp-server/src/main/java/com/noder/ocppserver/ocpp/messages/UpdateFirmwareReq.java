package com.noder.ocppserver.ocpp.messages;

import java.time.Instant;

public record UpdateFirmwareReq(String location, Integer retries, Instant retrieveDate, Integer retryInterval) {
}