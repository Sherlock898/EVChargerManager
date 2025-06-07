import api from "../api/apiClient";
import type { ChargingStation } from "../interfaces/ChargingStation";

const mockStations = {
  
}

const getUserStations = async () => {
  try {
    const response = await api.get('/api/v1/charging-stations')
    return response.data;
  } catch (error) {
    console.log("Error fetching stations", error)
    throw error;
  }
}

const registerStation = async (station: ChargingStation) => {
  try {
    const response = await api.post('api/v1/charging-stations', station);
    return response.data;
  } catch (error) {
    console.log("Error registering station", error);
    throw error;
  }
}

export default { getUserStations, registerStation }