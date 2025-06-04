package com.noder.restapi.dtos;

import java.time.Instant;

import com.noder.restapi.models.Connector;

public class TransactionCreateDTO {
    private Connector connector;
    private Instant start_time;
    private String start_meter_value;

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public void setStart_time(Instant start_time) {
        this.start_time = start_time;
    }

    public void setStartMeter_value(String start_meter_value) {
        this.start_meter_value = start_meter_value;
    }

    public Connector getConnector() {
        return connector;
    }

    public Instant getStart_time() {
        return start_time;
    }

    public String getStart_meter_value() {
        return start_meter_value;
    }
}
