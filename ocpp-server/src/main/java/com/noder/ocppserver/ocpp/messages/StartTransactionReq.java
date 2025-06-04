package com.noder.ocppserver.ocpp.messages;

import java.time.Instant;

public record StartTransactionReq(Integer connectorId, String idTag, Integer meterStart, Integer reservationId, Instant timestamp) {
    
}
