import axios from 'axios';
import type { Charger } from '../interfaces/Charger';

const getChargersByStationId = async (stationId: string): Promise<Charger[]> => {
  const response = await axios.get(`/api/v1/chargers/station/${stationId}`);
  return response.data;
};

const registerCharger = async (stationId: string, chargerData: { name: string }) => {
  return await axios.post(`/api/v1/chargers/station/${stationId}`, chargerData);
};

export default {
  getChargersByStationId,
  registerCharger,
};
