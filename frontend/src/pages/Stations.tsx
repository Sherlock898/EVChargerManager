import Sidebar from "../components/Sidebar";
import type { ChargingStation } from '../interfaces/ChargingStation';
import type { Charger } from '../interfaces/Charger';
import AddChargingStationForm from "../components/AddChargingStationForm";
import { useEffect, useState } from "react";
import stationService from '../services/chargingStationService';

const Stations = () => {
  const [stations, setStations] = useState<ChargingStation[]>([]);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [typeErrorMessage, setTypeErrorMessage] = useState<'success' | 'error' | null>(null);

  useEffect(() => {
    const getStations = async () => {
      try {
        const stations = await stationService.getUserStations()
        setStations(stations);
      } catch (err) {
        console.error(err);
        setErrorMessage('Error al cargar estaciones.');
      }
    }
    getStations();
  }, []);
  
  const handletationAdded = async () => {
    try {
      const updateStations = await stationService.getUserStations();
    } catch(error){

    }
  }
  
  return (
    <main className="min-h-screen bg-gradient-to-br from-[#003f4d] via-[#005f73] to-[#007595] py-10 px-4 flex justify-center">
      <Sidebar />
      <AddChargingStationForm onChargingStationAdded={function (): void {
        throw new Error("Function not implemented.");
      }} token={""} setErrorMessage={function (msg: string | null): void {
        throw new Error("Function not implemented.");
      }} setTypeErrorMessage={function (type: "success" | "error" | null): void {
        throw new Error("Function not implemented.");
      }} />

      {stations && 
        stations.map((station, index) => (
          <div key={index}>
            <h3>{station.name}</h3>
            <img src={station.photoURL} />
          </div>
        ))
      }

    </main>
  )
}

export default Stations;