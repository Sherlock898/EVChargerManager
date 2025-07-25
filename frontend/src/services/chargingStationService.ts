import api from "../api/apiClient";
import type { Charger, Connector } from "../interfaces/Charger";
import type { ChargingStationCreate } from "../interfaces/ChargingStation";

import type { ChargingStation } from "../interfaces/ChargingStation";
import type { WebSocketServerInfo } from "../interfaces/WebSocketServerInfo";


const getUserStations = async () => {
  try {
    const response = await api.get('/admin/stations');
    return response.data;
  } catch (error) {
    console.log("Error fetching stations", error)
    throw error;
  }
}

const registerStation = async (station: ChargingStationCreate) => {
  try {
    const response = await api.post('/admin/stations', station);
    return response.data;
  } catch (error) {
    console.log("Error registering station", error);
    throw error;
  }
}

export default { getUserStations, registerStation }