package com.noder.ocppserver.ocpp.messages;

import com.noder.ocppserver.ocpp.messages.Types.AuthorizationData;

public record SendLocalListReq(Integer listVersion, AuthorizationData[] localAuthorizationList, UpdateType updateType) {
    public enum UpdateType{
        Differential,
        Full
    };
}
