package com.noder.ocppserver.ocpp.messages.Types;
import java.time.Instant;

public record ChargingProfile(
    Integer chargingProfileId,
    Integer transactionId,
    Integer stackLevel,
    ChargingProfilePurposeType chargingProfilePurpose,
    ChargingProfileKindType chargingProfileKindType,
    RecurrencyKindType recurrencyKind,
    Instant validFrom,
    Instant validTo,
    ChargingSchedule chargingSchedule
) {
    public enum ChargingProfilePurposeType {
        ChargePointMaxProfile,
        TxDefaultProfile,
        TxProfile
    }

    public enum ChargingProfileKindType {
        Absolute,
        Recurring,
        Relative
    }

    public enum RecurrencyKindType {
        Daily,
        Weekly
    }
}