package com.noder.ocppserver.WebSocket;

import java.net.URI;
import java.time.Instant;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.noder.ocppserver.Types.ChargerManager;
import com.noder.ocppserver.Types.Utils.InstantAdapter;
import com.noder.ocppserver.ocpp.messages.AuthorizeConf;
import com.noder.ocppserver.ocpp.messages.AuthorizeReq;
import com.noder.ocppserver.ocpp.messages.BootNotificationConf;
import com.noder.ocppserver.ocpp.messages.BootNotificationReq;
import com.noder.ocppserver.ocpp.messages.CancelReservationReq;
import com.noder.ocppserver.ocpp.messages.ChangeAvailabilityReq;
import com.noder.ocppserver.ocpp.messages.ChangeAvailabilityReq.AvailabilityType;
import com.noder.ocppserver.ocpp.messages.DataTransferConf;
import com.noder.ocppserver.ocpp.messages.DataTransferReq;
import com.noder.ocppserver.ocpp.messages.DiagnosticsStatusNotificationConf;
import com.noder.ocppserver.ocpp.messages.DiagnosticsStatusNotificationReq;
import com.noder.ocppserver.ocpp.messages.FirmwareStatusNotificationConf;
import com.noder.ocppserver.ocpp.messages.FirmwareStatusNotificationReq;
import com.noder.ocppserver.ocpp.messages.GetCompositeScheduleReq.ChargingRateUnitType;
import com.noder.ocppserver.ocpp.messages.HeartbeatConf;
import com.noder.ocppserver.ocpp.messages.MeterValuesConf;
import com.noder.ocppserver.ocpp.messages.MeterValuesReq;
import com.noder.ocppserver.ocpp.messages.RemoteStopTransactionReq;
import com.noder.ocppserver.ocpp.messages.ResetReq.ResetType;
import com.noder.ocppserver.ocpp.messages.SendLocalListReq.UpdateType;
import com.noder.ocppserver.ocpp.messages.StartTransactionConf;
import com.noder.ocppserver.ocpp.messages.StartTransactionReq;
import com.noder.ocppserver.ocpp.messages.StatusNotificationConf;
import com.noder.ocppserver.ocpp.messages.StatusNotificationReq;
import com.noder.ocppserver.ocpp.messages.StopTransactionConf;
import com.noder.ocppserver.ocpp.messages.StopTransactionReq;
import com.noder.ocppserver.ocpp.messages.Types.AuthorizationData;
import com.noder.ocppserver.ocpp.messages.Types.ChargingProfile;
import com.noder.ocppserver.ocpp.messages.Types.ChargingProfile.ChargingProfilePurposeType;
import com.noder.ocppserver.ocpp.messages.Types.IdTagInfo;
import com.noder.ocppserver.ocpp.messages.Types.IdTagInfo.AuthorizationStatus;

/**
 * Class than handles OCPP messages recieved from the chargers.
 */
public class OcppHandler extends TextWebSocketHandler {

	private final Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
	private final int HEARTBEAT_INTERVAL_SECONDS = 30;
	private final ChargerManager chargerManager = ChargerManager.getInstance();
	private Deque<AbstractMap.SimpleEntry<String, String>> callResultIdQueue = new ArrayDeque<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		URI uri = session.getUri();
		if(uri == null){return;}
		String uris = uri.toString();
		String[] uriParts = uris.split("/");
        String id = uriParts[uriParts.length - 1];

		// Add charger object to manager, does nothing if already created
		chargerManager.addCharger(id, session);
		session.getAttributes().put("chargerId", id);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		// Two types of messages: call and call result
		// Call: [2, "UniqueId", "action", {payload}]
		// Call Result: [3, "UniqueId", {payload}]
		JsonArray deserializedMessage;
		try {
			deserializedMessage = JsonParser.parseString(message.getPayload()).getAsJsonArray();
		} catch (JsonParseException e) {
			System.err.println("Error parsing JSON: " + e.getMessage());
			return;
		}

		int messageId;
		try {
			messageId = deserializedMessage.get(0).getAsInt();
		} catch (Exception e) {
			System.err.println("Error parsing JSON: " + e.getMessage());
			return;
		}
		// Reroute to processing functions
		if (messageId == 2) processCall(session, deserializedMessage);
		else if(messageId == 3) processCallResult(session, deserializedMessage);

	}

	private void processCall(WebSocketSession session, JsonArray call){
		// Calls message structure:
		// [2, "UniqueId", "action", {payload}]

		// Check for message integrity: 4 fields, message id == 2
		if (call.size() != 4) {
			System.err.println("Error in message length");
			return;
		}
		String uniqueId;
		try {
			uniqueId = call.get(1).getAsString();
		} catch (Exception e) {
			System.err.println("Couldn't parse to string: " + call.get(1));
			return;
		}
		if (uniqueId == null) {
			System.err.println("Null uniqueId");
			return;
		}

		//Get action
		String action;
		try {
			action = call.get(2).getAsString();
		} catch (Exception e) {
			System.err.println("Error parsing action " + call.get(2));
			return;
		}
		if (action == null) {
			System.err.println("Null action");
			return;
		}

		// Get charger
		String chargerId = session.getAttributes().get("chargerId").toString();
		if(chargerId == null){
			// TODO: Don't print, do logs
			System.err.println("Error getting charger id from session");
		}
	
		// ------------------------------
		// ---------- Payload -----------
		// ------------------------------
		JsonElement payload = call.get(3);
		switch (action) {
			case "Authorize":
			AuthorizeReq authorizeReq = gson.fromJson(payload, AuthorizeReq.class);
			// TODO: We are authorizing everything, is this the intended behaviour?
			IdTagInfo idTagInfo = new IdTagInfo(null, authorizeReq.idTag(), IdTagInfo.AuthorizationStatus.Accepted);
			sendCallResult(session, uniqueId, new AuthorizeConf(idTagInfo));
			break;

			case "BootNotification":
				BootNotificationReq bootNotificationReq = gson.fromJson(payload, BootNotificationReq.class);
				chargerManager.loadBootNotificationInfo(chargerId, bootNotificationReq.chargePointModel(),
									bootNotificationReq.chargePointVendor(),
									bootNotificationReq.chargeBoxSerialNumber(),
									bootNotificationReq.chargeBoxSerialNumber(),
									bootNotificationReq.firmwareVersion(),
									bootNotificationReq.iccid(),
									bootNotificationReq.imsi(),
									bootNotificationReq.meterSerialNumber(),
									bootNotificationReq.meterType());
				// TODO: Check when to not accept boot notifications
				sendCallResult(session, uniqueId, new BootNotificationConf(Instant.now(), HEARTBEAT_INTERVAL_SECONDS, BootNotificationConf.Status.Accepted));
				break;

			case "DataTransfer":
				DataTransferReq dataTransferReq = gson.fromJson(payload, DataTransferReq.class);
				DataTransferConf dataTransferConf = new DataTransferConf(DataTransferConf.DataTransferStatus.Rejected, null);
				// TODO: Handle DataTransfer
				sendCallResult(session, uniqueId, dataTransferConf);
				break;

			case "DiagnosticsStatusNotification":
				DiagnosticsStatusNotificationReq diagnosticsStatusNotificationReq = gson.fromJson(payload, DiagnosticsStatusNotificationReq.class);
				// TODO: Handle DiagnosticsStatusNotification
				chargerManager.updateDiagnosticsStatus(chargerId, diagnosticsStatusNotificationReq.status());
				sendCallResult(session, uniqueId, new DiagnosticsStatusNotificationConf());
				break;

			// This message is used to notify the status of the firmware update.
			case "FirmwareStatusNotification":
				FirmwareStatusNotificationReq firmwareStatusNotificationReq = gson.fromJson(payload, FirmwareStatusNotificationReq.class);
				// TODO: Handle FirmwareStatusNotification
				chargerManager.updateFirmwareStatus(chargerId, firmwareStatusNotificationReq.status());
				sendCallResult(session, uniqueId, new FirmwareStatusNotificationConf());
				break;
			
			// This message is to verify that the connection is active.
			case "Heartbeat":
				sendCallResult(session, uniqueId, new HeartbeatConf(Instant.now()));
				break;

			case "MeterValues":
				MeterValuesReq meterValuesReq = gson.fromJson(payload, MeterValuesReq.class);
				// TODO: Handle Metervalues
				// If connectorId is zero, then it belongs to the charger.
				chargerManager.addMeterValues(meterValuesReq.connectorId(), chargerId, meterValuesReq.meterValue());
				sendCallResult(session, uniqueId, new MeterValuesConf());
				break;

			case "StartTransaction":
				StartTransactionReq startTransactionReq = gson.fromJson(payload, StartTransactionReq.class);
				// TODO: Send request to central api with: chargerId, connectorId, meterstart, timestamp
				Integer transactionId = chargerManager.startTransaction(chargerId, startTransactionReq.connectorId(), startTransactionReq.meterStart(), startTransactionReq.timestamp());
				sendCallResult(session, uniqueId, new StartTransactionConf(new IdTagInfo(null, null, AuthorizationStatus.Accepted), transactionId));
				break;

			case "StatusNotification":
				StatusNotificationReq statusNotificationReq = gson.fromJson(payload, StatusNotificationReq.class);
				chargerManager.updateChargerStatus(chargerId, statusNotificationReq.connectorId(), statusNotificationReq.status(), statusNotificationReq.errorCode());
				sendCallResult(session, uniqueId, new StatusNotificationConf());
				break;

			case "StopTransaction":
				StopTransactionReq stopTransactionReq = gson.fromJson(payload, StopTransactionReq.class);
				chargerManager.stopTransaction(chargerId, stopTransactionReq.transactionId(), stopTransactionReq.meterStop(), stopTransactionReq.timestamp());
				sendCallResult(session, uniqueId, new StopTransactionConf(null));
				break;
		}
	}

	private void processCallResult(WebSocketSession session, JsonArray callResult){
		// Call result message structure:
		// [3, "UniqueId", {payload}]

		// Check for message integrity: 3 fields, message id == 3
		if (callResult.size() != 3) {
			System.err.println("Error in message length");
			return;
		}
		String uniqueId;
		try {
			uniqueId = callResult.get(1).getAsString();
		} catch (Exception e) {
			System.err.println("Couldn't parse to string: " + callResult.get(1));
			return;
		}
		if (uniqueId == null) {
			System.err.println("Null uniqueId");
			return;
		}

		// Get message type from callResult ids queue
		String messageType = "";
        Iterator<AbstractMap.SimpleEntry<String, String>> iterator = callResultIdQueue.iterator();
        while (iterator.hasNext()) {
            AbstractMap.SimpleEntry<String, String> entry = iterator.next();
            if (entry.getKey().equals(uniqueId)) {
                messageType = entry.getValue();
                iterator.remove(); // Remove from queue
                break;
            }
        }

		switch (messageType){
			case "CancelReservation":
				break;
			case "ChangeAvailability":
				break;
			case "ChangeConfiguration":
				break;
			case "ClearCache":
				break;
			case "ClearChargingProfile":
				break;
			case "DataTransfer":
				break;
			case "GetCompositeSchedule":
				break;
			case "GetConfiguration":
				break;
			case "GetDiagnostics":
				break;
			case "GetLocalListVersion":
				break;
			case "RemoteStartTransaction":
				break;
			case "RemoteStopTransaction":

				break;
			case "ReserveNow":
				break;
			case "Reset":
				break;
			case "SendLocalList":
				break;
			case "SetChargingProfile":
				break;
			case "TriggerMessage":
				break;
			case "UnlockConnector":
				break;
			case "UpdateFirmware":
				break;
			default:
				throw new IllegalStateException("Unrecognized operation: " + messageType);
		}
		
	}

	public void sendCancelReservation(String chargerId, Integer reservationId){
		// TODO: All reservation related classes and logic
		CancelReservationReq cancelReservationReq = new CancelReservationReq(reservationId);
		WebSocketSession session = chargerManager.getSession(chargerId);
		if (session == null) {
			System.err.println("Error, charger session not found. Id: " + chargerId);
			return;
		}
		sendCall(session, "CancelReservation", cancelReservationReq);
	}

	public void sendChangeAvailability(String chargerId, int connectorId, AvailabilityType type){
		// TODO: Implement
		ChangeAvailabilityReq changeAvailabilityReq = new ChangeAvailabilityReq(connectorId, type);
		
	}

	public void sendChangeConfiguration(String chargerId, String key, String value){
		// TODO: Implement
	}

	public void sendClearCache(String chargerId){
		// TODO: Implement
	}

	public void sendClearChargingProfile(String chargerId, Integer id, Integer connectorId, ChargingProfilePurposeType chargingProfilePurpose, Integer stackLevel){
		// TODO: Implement
	}

	public void sendDataTransfer(String chargerId, String vendorId, String messageId, String data){
		// TODO: Implement
	}
	
	public void sendGetCompositeSchedule(String chargerId, Integer connectorId, Integer duration, ChargingRateUnitType chargingRateUnit){
		// TODO: Implement
	}

	public void sendGetConfiguration(String chargerId, String key){
		// TODO: Implement
	}

	public void sendGetDiagnostics(String chargerId, String location, Integer retries, Integer retryeInterval, Instant startTime, Instant stopTime){
		// TODO: Implement
	}

	public void sendGetLocalListVersion(String chargerId){
		// TODO: Implement
	}

	public void sendRemoteStartTransaction(String chargerId, Integer connectorId, String idToken, ChargingProfile chargingProfile){
		// TODO: Implement
	}

	public void sendRemoteStopTransaction(String chargerId, Integer transactionId){
		// TODO: Implement
		WebSocketSession session = chargerManager.getSession(chargerId);
		if(session == null) throw new IllegalStateException("No active session found for chargerId: " + chargerId);
		sendCall(session, "RemoteStopTransaction", new RemoteStopTransactionReq(transactionId));
	}

	public void sendReserveNow(String chargerId, int connectorId, Instant expiryDate, String idTag, String parentIdTag, int reservationId){
		// TODO: Implement
	}

	public void sendReset(String chargerId, ResetType type){
		// TODO: Implement
	}

	public void sendLocalList(String chargerId, Integer listVersion, AuthorizationData localAuthorizationList, UpdateType updateType){
		// TODO: Implement
	}

	public void sendSetChargingProfile(String chargerId, int connectorId, ChargingProfile csChargingProfile){
		// TODO: Implement
	}

	public void sendTriggerMessage(String chargerId, String requestedMessage, Integer connectorId){
		// TODO: Implement
	}

	public void sendUnlockConnector(String chargerId, int connectorId){
		// TODO: Implement
	}

	public void sendUpdateFirmware(String chargerId, String location, Instant retrieveDate, Integer retryInterval){
		// TODO: Implement
	}
	
	private String generateUniqueId() {
		return java.util.UUID.randomUUID().toString();
	}

	// Send call
	private void sendCall(WebSocketSession session, String messageType, Object payload){
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(2);
		String uniqueId = generateUniqueId();
		jsonArray.add(uniqueId);
		jsonArray.add(messageType);
		jsonArray.add(gson.toJsonTree(payload));
		try {
			session.sendMessage(new TextMessage(gson.toJson(jsonArray)));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		callResultIdQueue.add(new AbstractMap.SimpleEntry<>(uniqueId, messageType));
	}

	// Send callResult
	private void sendCallResult(WebSocketSession session, String uniqueId, Object payload) {
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(3);
		jsonArray.add(uniqueId);
		jsonArray.add(gson.toJsonTree(payload));
		try {
			session.sendMessage(new TextMessage(gson.toJson(jsonArray)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
