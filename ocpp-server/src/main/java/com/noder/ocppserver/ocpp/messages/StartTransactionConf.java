package com.noder.ocppserver.ocpp.messages;

import com.noder.ocppserver.ocpp.messages.Types.IdTagInfo;

public record StartTransactionConf(IdTagInfo idTagInfo, Integer transactionId) {
    
}
