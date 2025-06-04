package com.noder.ocppserver.ocpp.messages;
import java.time.Instant;

import com.noder.ocppserver.ocpp.messages.Types.ChargingSchedule;

public record GetCompositeScheduleConf(Status status, Integer connectorId, Instant scheduleStart, ChargingSchedule chargingSchedule) {
    public enum Status {
        Accepted,
        Rejected
    }
}
