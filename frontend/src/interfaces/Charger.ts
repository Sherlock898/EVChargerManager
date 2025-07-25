import type { ChargingStation } from "./ChargingStation"
import type { WebSocketServerInfo } from "./WebSocketServerInfo"

export interface Charger {
  id: number,
  name: string,
  location: string,
  connector_count: number,
  uri: string,
  key: string,
  status: string
  connectors: Connector[],
  chargingStation: ChargingStation,
  webSocketServer: WebSocketServerInfo
};

export interface ChargerCreate {
  name: string;
  location?: string;
  connector_count?: number;
}

export interface Connector {
  id: number,
  connector_id: number,
  status: string,
  charger: Charger
};