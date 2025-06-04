package com.noder.ocppserver.ocpp.messages;
import com.noder.ocppserver.ocpp.messages.Types.ConfigurationKey;

public record GetConfigurationConf(ConfigurationKey[] configurationKey, String[] unknownKey) {}