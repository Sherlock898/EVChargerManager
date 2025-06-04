package com.noder.ocppserver.ocpp.messages;

import java.time.Instant;

public record ReserveNowReq(Integer connectorId, Instant expiryDate, String idTag, String parentIdTag, Integer reservationId) {
    
}
