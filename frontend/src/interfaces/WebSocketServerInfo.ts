import type { Charger } from "./Charger";

export interface WebSocketServerInfo {
  id: number,
  url: string,
  chargers: Charger[]
}