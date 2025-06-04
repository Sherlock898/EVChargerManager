package com.noder.ocppserver.ocpp.messages;

import com.noder.ocppserver.ocpp.messages.Types.ChargingProfile;

public record SetChargingProfileReq(Integer connectorId, ChargingProfile csChargingProfiles) {

}
