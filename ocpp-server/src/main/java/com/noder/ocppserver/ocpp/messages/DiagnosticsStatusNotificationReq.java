package com.noder.ocppserver.ocpp.messages;

public record DiagnosticsStatusNotificationReq(StatusDiagnostics status) {
    public enum StatusDiagnostics {
        Idle,
        Uploaded,
        UploadFailed,
        Uploading
    }
}
