package com.noder.ocppserver.Types;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import com.noder.ocppserver.ocpp.messages.ChangeAvailabilityReq.AvailabilityType;
import com.noder.ocppserver.ocpp.messages.StatusNotificationReq.ChargePointErrorCode;
import com.noder.ocppserver.ocpp.messages.StatusNotificationReq.ChargePointStatus;
import com.noder.ocppserver.ocpp.messages.Types.MeterValue;


/**
 * Class that represents a connector in a charge point.
 * It contains all the information about the connector.
 * A charge point might have multiple connectors.
 */
public class Connector {
    private int id;
    private ChargePointStatus status;
    private ChargePointErrorCode errorCode;
    private Deque<MeterValue> meterValues;
    private AvailabilityType availabilityType;

    public Connector(int id){
        this.id = id;
        this.meterValues = new ArrayDeque<>();
    }

    public void updateStatus(ChargePointStatus status, ChargePointErrorCode errorCode){
        this.status = status;
        this.errorCode = status == ChargePointStatus.Faulted ? errorCode : null;
    }

    public void addMeterValues(MeterValue[] meterValue) {
        for(int i = 0; i < meterValue.length; i++) {
            meterValues.add(meterValue[i]);
        }
    }

    public void setAvailabilityType(AvailabilityType availabilityType) {
        this.availabilityType = availabilityType;
    }

    public AvailabilityType getAvailabilityType() {
        return availabilityType;
    }

    /**
     * Method to clean the meter values of the connector that are older than the threshold time.
     * @param thresholdTime The threshold time to clean the meter values.
     */
    public void cleanMeterValues(long thresholdTime) {
        synchronized (meterValues) {
            Iterator<MeterValue> iterator = meterValues.iterator();
            while(iterator.hasNext()) {
                MeterValue meterValue = iterator.next();
                if(meterValue.timestamp().toEpochMilli() <= thresholdTime) {
                    iterator.remove();
                }
                else {
                    break;
                }
            }
        }
    }
}