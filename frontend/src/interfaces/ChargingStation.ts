import type { Charger } from "./Charger";

export interface ChargingStation {
  id: string;
  name: string;
  location: string;
  photoUrl: string;
  info: string;
  chargers: Charger[]
}

export interface ChargingStationCreate {
  name: string,
  location: string,
  photoUrl: string,
  info: string
}