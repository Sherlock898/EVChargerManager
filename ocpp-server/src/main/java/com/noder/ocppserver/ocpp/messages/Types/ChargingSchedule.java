package com.noder.ocppserver.ocpp.messages.Types;

import java.time.Instant;

public record ChargingSchedule(Integer duration, Instant startSchedule, ChargingRateUnit chargingRateUnit, ChargingSchedulePeriod[] chargingSchedulePeriod, Double minChargingRate) {
    public enum ChargingRateUnit {
        W,
        A
    }
}
