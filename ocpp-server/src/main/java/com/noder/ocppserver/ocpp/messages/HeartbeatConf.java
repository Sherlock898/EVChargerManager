package com.noder.ocppserver.ocpp.messages;
import java.time.Instant;

public record HeartbeatConf(Instant currentTime) {}
