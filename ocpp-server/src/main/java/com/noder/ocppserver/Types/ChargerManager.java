package com.noder.ocppserver.Types;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.WebSocketSession;

import com.noder.ocppserver.Types.Exceptions.ChargerNotFoundException;
import com.noder.ocppserver.ocpp.messages.ChangeAvailabilityReq.AvailabilityType;
import com.noder.ocppserver.ocpp.messages.DiagnosticsStatusNotificationReq.StatusDiagnostics;
import com.noder.ocppserver.ocpp.messages.FirmwareStatusNotificationReq.StatusFirmware;
import com.noder.ocppserver.ocpp.messages.StatusNotificationReq.ChargePointErrorCode;
import com.noder.ocppserver.ocpp.messages.StatusNotificationReq.ChargePointStatus;
import com.noder.ocppserver.ocpp.messages.Types.MeterValue;


public class ChargerManager {
    public static final ChargerManager instance = new ChargerManager();
    public final Map<String, Charger> chargers = new ConcurrentHashMap<>();
    public final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduler;
    private static final long ONE_WEEK_IN_MILLIS = TimeUnit.DAYS.toMillis(7);

    private ChargerManager() {}

    public static ChargerManager getInstance() {
        return instance;
    }

    /**
     * Adds a charger session to the dictionary with the respective id.
     * Adds a Charger instance object with its id if not present.
     * @param id The id of the charger.
     * @param session The WebSocketSession of the charger.
     * @return The Charger instance object.
     */
    public Charger addCharger(String id, WebSocketSession session) {
        sessions.put(id, session);
        return chargers.computeIfAbsent(id, key -> new Charger(id));
    }

    public WebSocketSession getSession(String chaString) {
        return sessions.get(chaString);
    }
    
    /**
     * Removes a charger session from the dictionary with the respective id. Only session is removed.
     * @param id The id of the charger to be removed.
     */
    public void removeChargerSession(String id){
        sessions.remove(id);
    }

    /**
     * Removes a charger and it's session from the dictionary with the respective id.
     * @param id The id of the charger to be removed.
     */
    public void removeCharger(String id){
        sessions.remove(id);
        chargers.remove(id);
    }
    
    /**
     * Returns the Charger instance object with the respective id.
     * @param id The id of the charger.
     * @return The Charger instance object.
     * @throws ChargerNotFoundException If the charger with the respective id is not found.
     */
    public Charger getCharger(String id) throws ChargerNotFoundException {
        Charger charger = chargers.get(id);
        if (charger == null) {
            throw new ChargerNotFoundException("Charger with id" + id + "not found.\n");
        }
        return charger;
    }

    public Map<String, Charger> getAllChargers() {
        return chargers;
    }

    // Updates Charger with the information of BootNotification call
    /**
     * Updates the charger with the information within the BootNotification call.
     * @return The updated charger.
     * @throws ChargerNotFoundException If the charger with the respective id is not found.
     */
    public Charger loadBootNotificationInfo(String id, String model, String vendor, String boxSerialNumber, String chargePointSerialNumber,
                String firmwareVersion, String iccid, String imsi, String meterSerialNumber, String meterType) throws ChargerNotFoundException{
        Charger charger;
        try {
            charger = this.getCharger(id);
        } catch (ChargerNotFoundException e) {
            throw e;
        }
        charger.loadBootNotificationInfo(model, vendor, boxSerialNumber, chargePointSerialNumber, firmwareVersion, iccid, imsi, meterSerialNumber, meterType);
        return charger;
    }
    
    // Adds a MeterValue reading to the charger
    public void addMeterValues(Integer connectorId, String chargerId, MeterValue[] meterValue) {
        chargers.get(chargerId).addMeterValues(connectorId, chargerId, meterValue);
    }

    public void setAvailabilityType(String chargerId, Integer connectorId, AvailabilityType availabilityType) {
        chargers.get(chargerId).setAvailabilityType(connectorId, availabilityType);
    }

    /*public AvailabilityType getAvailabilityType(String chargerId, Integer connecitrId) {
        chargers.get(chargerId).getAvailabilityType(connecitrId);
    }*/
    
    public void startMeterValueCleanupTask() {
        if (scheduler == null) {
            scheduler = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> cleanAllChargersMeterValues(), 1, 1, TimeUnit.DAYS);
        }
    }

    private void cleanAllChargersMeterValues() {
        long oneWeekAgo = System.currentTimeMillis() - ONE_WEEK_IN_MILLIS;
        for (Charger charger : chargers.values()) {
            charger.cleanMeterValues(oneWeekAgo);
        }
    }

    // Method to update the diagnostic status of a charger
    public void updateDiagnosticsStatus(String chargerId, StatusDiagnostics statusDiagnostics) throws ChargerNotFoundException {
        Charger charger = getCharger(chargerId);
        if (charger == null) {
            System.err.println("Charger not found for ID: " + chargerId);
            return;
        }
        charger.setDiagnosticsStatus(statusDiagnostics);
    }

    public void updateFirmwareStatus(String chargerId, StatusFirmware statusFirmware) {
        Charger charger = getCharger(chargerId);
        if (charger == null) {
            System.err.println("Charger not found for ID: " + chargerId);
            return;
        }
        charger.setFirmwareStatus(statusFirmware);
    }

    public void updateChargerStatus(String chargerId, int connectorId, ChargePointStatus status, ChargePointErrorCode errorCode) throws ChargerNotFoundException{
        Charger charger;
        try {
            charger = getCharger(chargerId);
        } catch (ChargerNotFoundException e) {
            throw e;
        }
        charger.updateStatus(connectorId, status, errorCode);
    }

    // Register start of a new transaction on charger and returns it's assigned id
    public Integer startTransaction(String chargerId, int connectorId, Integer meterStart, Instant startDate){
        Charger charger;
        try {
            charger = getCharger(chargerId);
        } catch (ChargerNotFoundException e) {
            throw e;
        }
        return charger.startTransaction(connectorId, meterStart, startDate);
    }

    // Register stop of an existing transaction
    public void stopTransaction(String chargerId, Integer transactionId, Integer meterStop, Instant timestamp) {
        Charger charger;
        try {
            charger = getCharger(chargerId);
        } catch (ChargerNotFoundException e) {
            System.err.println("Charger not found for ID: " + chargerId);
            return;
        }
        charger.stopTransaction(transactionId, meterStop, timestamp);
    }

}
