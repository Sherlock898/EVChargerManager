package com.noder.ocppserver.ocpp.messages;

import java.time.Instant;

public record StatusNotificationReq(Integer connectorId, ChargePointErrorCode errorCode, String info, ChargePointStatus status, Instant dateTime, String vendorId, String vendorErrorCode) {
    public enum ChargePointErrorCode{
        ConnectorLockFailure,
        EVCommunicationError,
        GroundFailure,
        HighTemperature,
        InternalError,
        LocalListConflict,
        NoError,
        OtherError,
        OverCurrentFailure,
        OverVoltage,
        PowerMeterFailure,
        PowerSwitchFailure,
        ReaderFailure,
        ResetFailure,
        UnderVoltage,
        WeakSignal,
    };
    
    public enum ChargePointStatus{
        Available,
        Preparing,
        Charging,
        SuspendedEVSE,
        SuspendedEV,
        Finishing,
        Reserved,
        Unavailable,
        Faulted
    };
}
