package com.noder.ocppserver.ocpp.messages.Types;
import java.time.Instant;

public record MeterValue(Instant timestamp, SampledValue[] sampledValue) {
}