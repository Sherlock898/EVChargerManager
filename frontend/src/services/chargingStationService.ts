import api from "../api/apiClient";
import type { Charger, Connector } from "../interfaces/Charger";
import type { ChargingStationCreate } from "../interfaces/ChargingStation";

import type { ChargingStation } from "../interfaces/ChargingStation";
import type { WebSocketServerInfo } from "../interfaces/WebSocketServerInfo";

const ws1: WebSocketServerInfo = {
  id: 1,
  url: "wss://ws.example.com/evsocket",
  chargers: [] // se llena luego
}

const station1: ChargingStation = {
  id: "cs-001",
  name: "Estación Central",
  location: "Av. Libertador 1000",
  photoURL: "https://media.istockphoto.com/id/1453953453/photo/strategy-of-diversified-investment.jpg?s=612x612&w=0&k=20&c=GdKGA5EuK0QfKm76ExjkK64iPZLuTUOyIDQlXs-ZRQM=",
  info: "Carga rápida disponible 24/7.",
  chargers: [] // se llena luego
}

const station2: ChargingStation = {
  id: "cs-002",
  name: "Carga Mall Norte",
  location: "Mall Norte, P4",
  photoURL: "https://www.karrass.com/_next/static/media/Negotiating_around_table.a34c0d98.jpg",
  info: "Ubicada en estacionamiento subterráneo.",
  chargers: [] // se llena luego
}

// ------- Chargers -------
const charger1: Charger = {
  id: 1,
  location: "Plaza frontal",
  connector_count: 2,
  uri: "/chargers/1",
  key: "CHG-CS001-A",
  chargerStatus: "AVAILABLE",
  chargingStation: station1,
  webSocketServer: ws1,
  connectors: [] // se llena luego
}

const charger2: Charger = {
  id: 2,
  location: "Zona sur",
  connector_count: 1,
  uri: "/chargers/2",
  key: "CHG-CS002-A",
  chargerStatus: "CHARGING",
  chargingStation: station2,
  webSocketServer: ws1,
  connectors: [] // se llena luego
}

// ------- Connectors -------
const connector1: Connector = {
  id: 100,
  connector_id: 1,
  status: "AVAILABLE",
  charger: charger1
}

const connector2: Connector = {
  id: 101,
  connector_id: 2,
  status: "CHARGING",
  charger: charger1
}

const connector3: Connector = {
  id: 102,
  connector_id: 1,
  status: "UNAVAILABLE",
  charger: charger2
}

// Asignar conectores a chargers
charger1.connectors = [connector1, connector2]
charger2.connectors = [connector3]

// Asignar chargers a estaciones
station1.chargers = [charger1]
station2.chargers = [charger2]

// Asignar chargers al WebSocket server
ws1.chargers = [charger1, charger2]

// -------- Export --------
export const mockStations: ChargingStation[] = [station1, station2, station1, station2, station1, station2]


const getUserStations = async () => {
  return mockStations;
  try {
    const response = await api.get('/api/v1/charging-stations')
    return response.data;
  } catch (error) {
    console.log("Error fetching stations", error)
    throw error;
  }
}

const registerStation = async (station: ChargingStationCreate) => {
  try {
    const response = await api.post('api/v1/charging-stations', station);
    return response.data;
  } catch (error) {
    console.log("Error registering station", error);
    throw error;
  }
}

export default { getUserStations, registerStation }