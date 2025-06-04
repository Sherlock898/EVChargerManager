package com.noder.ocppserver.Types;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

import com.noder.ocppserver.Types.Transaction.TransactionStatus;
import com.noder.ocppserver.ocpp.messages.ChangeAvailabilityReq.AvailabilityType;
import com.noder.ocppserver.ocpp.messages.DiagnosticsStatusNotificationReq.StatusDiagnostics;
import com.noder.ocppserver.ocpp.messages.FirmwareStatusNotificationReq.StatusFirmware;
import com.noder.ocppserver.ocpp.messages.StatusNotificationReq.ChargePointErrorCode;
import com.noder.ocppserver.ocpp.messages.StatusNotificationReq.ChargePointStatus;
import com.noder.ocppserver.ocpp.messages.Types.MeterValue;


/**
 * Charger class that represents a charger in the system.
 * It contains all the information about the charger and its connectors.
 * It contains information about current transactions and meter values.
 */

public class Charger {
    // Parameters that should be provided by the charger
    private String id;
    private ChargePointStatus status;
    private ChargePointErrorCode errorCode;
    private StatusDiagnostics statusDiagnostics;
    private StatusFirmware statusFirmware;
    private AvailabilityType availabilityType;
    private ArrayList<Connector> connectors;
    private ArrayDeque<MeterValue> meterValues;
    private String model;
    private String vendor;


    // Optional parameters, if not provided by the charger they will be null
    public String boxSerialNumber;
    public String chargePointSerialNumber;
    public String firmwareVersion;
    public String iccid;
    public String imsi;
    public String meterSerialNumber;
    public String meterType;

    // List of transactions
    private ArrayDeque<Transaction> transactions;

    public Charger(String id){
        this.id = id;
    }

    /**
     * Loads the boot notification information of the charger.
     *
     * @param model The model of the charger.
     * @param vendor The vendor of the charger.
     * @param boxSerialNumber The box serial number of the charger.
     * @param chargePointSerialNumber The charge point serial number of the charger.
     * @param firmwareVersion The firmware version of the charger.
     * @param iccid The ICCID of the charger.
     * @param imsi The IMSI of the charger.
     * @param meterSerialNumber The meter serial number of the charger.
     * @param meterType The meter type of the charger.
     */
    public void loadBootNotificationInfo(String model, String vendor, String boxSerialNumber, String chargePointSerialNumber,
                String firmwareVersion, String iccid, String imsi, String meterSerialNumber, String meterType) {
        this.model = model;
        this.vendor = vendor;
        this.boxSerialNumber = boxSerialNumber;
        this.chargePointSerialNumber = chargePointSerialNumber;
        this.firmwareVersion = firmwareVersion;
        this.iccid = iccid;
        this.imsi = imsi;
        this.meterSerialNumber = meterSerialNumber;
        this.meterType = meterType;
        this.connectors = new ArrayList<>();
        this.transactions = new ArrayDeque<>();
    }
    
    /**
     * Adds a connector to the charger's connector array.
     *
     * @param connector The connector to load.
     */
    public void addConnector(Connector connector) {
        connectors.add(connector);
    }

    public String getId() {
        return id;
    }

    /**
     * Adds a MeterValue reading to the charger.
     * @param connectorId Id of the connector to which the meter value belongs, 0 if the meter value is for the charger.
     * @param chargerId Id of the charger.
     * @param meterValue The meter values to add
     */
    public void addMeterValues(Integer connectorId, String chargerId, MeterValue[] meterValue) {
        if(connectorId == 0) {
            for(int i = 0; i < meterValue.length; i++) {
                meterValues.add(meterValue[i]);
            }
        }
        else {
            connectors.get(connectorId).addMeterValues(meterValue);
        }
    }

    /**
     * Method to clean the meter values of the charger that are older than the threshold time.
     * @param thresholdTime The threshold time to clean the meter values.
     */
    // TODO: Clean charger meter values
    public void cleanMeterValues(long timeTreshold) {
        for (Connector connector : connectors) {
            if (connector != null) {
                connector.cleanMeterValues(timeTreshold);
            }
        }
    }

    public void setDiagnosticsStatus(StatusDiagnostics statusDiagnostics) {
        this.statusDiagnostics = statusDiagnostics;
    }

    public StatusDiagnostics getDiagnosticsStatus() {
        return statusDiagnostics;
    }

    public void setFirmwareStatus(StatusFirmware statusFirmware) {
        this.statusFirmware = statusFirmware;
    }

    public StatusFirmware getFirmwareStatus() {
        return statusFirmware;
    }

    public void setAvailabilityType(Integer connectorId, AvailabilityType availabilityType) {
        if(connectorId == 0) {
            this.availabilityType = availabilityType;
        }
        else {
            connectors.get(connectorId).setAvailabilityType(availabilityType);
        }
    }

    public AvailabilityType getAvailabilityType(Integer connectorId) {
        if(connectorId == 0) {
            return availabilityType;
        }
        else {
            return connectors.get(connectorId).getAvailabilityType();
        }
    }

    public void updateStatus(int id, ChargePointStatus status, ChargePointErrorCode errorCode) {
        if (id == 0) { // Update status of charger
            this.status = status;
            this.errorCode = status == ChargePointStatus.Faulted ? errorCode : null;
            return;
        }
        if (id - 1 >= connectors.size()) { // If id not registered register all connectors till that id, (id are incremental from 1)
            while (id - 1 >= connectors.size()) {
                connectors.add(new Connector(connectors.size()));
            }
        }
        connectors.get(id - 1).updateStatus(status, errorCode);
    }

    /**
     * Register the start of a new transaction on the charger and return the transaction id.
     * @param connectorId The id of the connector on which the transaction is started.
     * @param meterStart Meter value reading at the start of the transaction.
     * @param startDate The start instant of the transaction.
     * @return generated transaction id
     */
    public Integer startTransaction(int connectorId, Integer meterStart, Instant startDate){
        Transaction transaction = new Transaction(connectorId, meterStart, startDate);
        this.transactions.add(transaction);
        return transaction.getTransactionId();
    }

    /**
     * Stop a transaction on the charger.
     * The total energy consumed is calculated as the delta of meter values at start and end.
     * @param transactionId The id of the transaction to stop.
     * @param meterStop Meter value reading at the stop of the transaction
     * @param timestamp The stop instant of the transaction.
     */
    public void stopTransaction(Integer transactionId, Integer meterStop, Instant timestamp) {
        Iterator<Transaction> iterator = transactions.descendingIterator();
        while (iterator.hasNext()) {
            Transaction transaction = iterator.next();
            if (transaction.getTransactionId() == transactionId) {
                transaction.endTransaction(meterStop);
                return;
            }
        }
    }

    /**
     * Get the status of a transaction.
     * @param id The id of the transaction.
     * @return The status of the transaction.
     */
    public TransactionStatus getTransactionStatus(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == id) {
                return transaction.getTransactionStatus();
            }
        }
        return null;
    }
}