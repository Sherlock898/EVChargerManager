package com.noder.ocppserver.ocpp.messages;

public record FirmwareStatusNotificationReq(StatusFirmware status) {
    public enum StatusFirmware {
        Downloaded,
        DownloadFailed,
        Downloading,
        Idle,
        InstallationFailed,
        Installing,
        Installed
    }
}
