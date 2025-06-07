import type { Charger } from "./Charger";

export interface ChargingStation {
  id: string;
  name: string;
  location: string;
  photoURL: string;
  info: string;
  chargers: Charger[]
}