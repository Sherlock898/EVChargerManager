package com.noder.ocppserver.ocpp.messages.Types;

// TODO: In the ocpp manual these are defined as enumeration but for simplicity we defined them as strings
// maybe we should use enum instead? however some have names we can't 
public record SampledValue(String value, String context, String format, String measurand, String phase, String location, String unit) {
}