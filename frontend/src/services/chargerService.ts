import api from '../api/apiClient';
import type { Charger } from '../interfaces/Charger';

const getChargersByStationId = async (stationId: string): Promise<Charger[]> => {
  // TODO: This might need to be moved to chargingStationService
  const response = await api.get(`/admin/stations/${stationId}/chargers`);
  return response.data;
};

const registerCharger = async (chargerData: { name: string, stationId: string }) => {
  const response = await api.post(`/admin/chargers`, chargerData);
  return response.data;
};

export default {
  getChargersByStationId,
  registerCharger,
};
