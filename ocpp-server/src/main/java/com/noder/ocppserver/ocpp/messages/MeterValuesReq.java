package com.noder.ocppserver.ocpp.messages;

import com.noder.ocppserver.ocpp.messages.Types.MeterValue;

public record MeterValuesReq(Integer connectorId, Integer transactionId, MeterValue[] meterValue) {}
