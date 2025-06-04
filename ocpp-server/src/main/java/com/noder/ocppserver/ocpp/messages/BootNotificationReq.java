package com.noder.ocppserver.ocpp.messages;


public record BootNotificationReq(String chargeBoxSerialNumber, String chargePointModel, String  chargePointSerialNumber, String chargePointVendor, String firmwareVersion, String iccid, String imsi, String meterSerialNumber, String meterType) {}


