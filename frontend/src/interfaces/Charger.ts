import type { ChargingStation } from "./ChargingStation"
import type { WebSocketServerInfo } from "./WebSocketServerInfo"

export interface Charger {
  id: number,
  location: string,
  connector_count: number,
  uri: string,
  key: string,
  chargerStatus: string
  connectors: Connector[],
  chargingStation: ChargingStation,
  webSocketServer: WebSocketServerInfo
};

export interface Connector {
  id: number,
  connector_id: number,
  status: string,
  charger: Charger
};