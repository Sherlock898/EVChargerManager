package com.noder.ocppserver.ocpp.messages;

import com.noder.ocppserver.ocpp.messages.Types.ChargingProfile;

public record RemoteStartTransactionReq(Integer connectorId, String idTag, ChargingProfile chargingProfile) {
    
}
