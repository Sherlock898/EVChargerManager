import { useLocation, useParams } from "react-router";
import { useState } from "react";
import type { ChargingStation } from "../interfaces/ChargingStation";
import AddChargerForm from "../components/AddChargerForm";
import chargerService from '../services/chargerService';

const StationDetails = () => {
  const [showChargerForm, setShowChargerForm] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [typeErrorMessage, setTypeErrorMessage] = useState<'success' | 'error' | null>(null);
  const location = useLocation()
  const station = (location.state as { station: ChargingStation })?.station
  const [chargers, setChargers] = useState(station?.chargers || []);
  const { stationId } = useParams<{ stationId: string }>();
  
  const handleChargerAdded = async () => {
    if (!stationId) return;

    try {
      const updatedChargers = await chargerService.getChargersByStationId(stationId);
      setChargers(updatedChargers);
      setErrorMessage("Cargador agregado exitosamente.");
      setTypeErrorMessage("success");
      setShowChargerForm(false);

      setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
    } catch (err) {
      console.error(err);
      setErrorMessage("Error al actualizar la lista de cargadores.");
      setTypeErrorMessage("error");
      setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
    }
  };
  
  if (!station) return <p>No se pudo cargar información de la estación</p>

  return (
    <>
      {showChargerForm && (
        <div className="fixed inset-0 bg-black/30 backdrop-blur-sm flex items-center justify-center z-50">
          <div className="bg-white rounded-md p-6 shadow-lg max-w-lg w-full relative z-50">
            <button
              className="absolute top-2 right-2 text-gray-500 hover:text-gray-700"
              onClick={() => setShowChargerForm(false)}
            >
              ✕
            </button>
            <AddChargerForm
              stationId={stationId!}
              onChargerAdded={handleChargerAdded}
              setErrorMessage={setErrorMessage}
              setTypeErrorMessage={setTypeErrorMessage}
            />
          </div>
        </div>
      )}

      <main className="p-6 flex-1">
        <h2 className="text-2xl font-bold mb-6">{station.name}</h2>
        <div className="flex flex-col md:flex-row gap-8">
          <div className="flex-1">
            <div className="bg-white rounded-full overflow-hidden w-48 h-48 flex items-center justify-center mb-6">
              <img src={station.photoUrl} alt={station.name} className="w-full h-full object-cover" />
            </div>
            <p className="mb-2 text-gray-700">{station.info}</p>
            <p className="text-gray-700">{station.location}</p>
          </div>
          <div className="flex-1">
            <h3 className="text-xl font-semibold border-b pb-2 mb-4">Lista de cargadores</h3>
            <ul className="space-y-4">
              {chargers.map((charger, index) => (
                <li key={index} className="flex items-center gap-2">
                  <span className="font-medium">• Nombre cargador {index + 1}</span>
                  <span className="w-4 h-4 bg-blue-200 rounded-full"></span>
                  {charger.chargerStatus && <span className="text-gray-600">({charger.chargerStatus})</span>}
                </li>
              ))}
            </ul>

            <div className="mt-10 flex justify-center">
              <button
                onClick={() => setShowChargerForm(true)}
                className="px-6 py-2 bg-cyan-700 text-white rounded-md hover:bg-cyan-800 transition"
              >
                + Añadir Cargador
              </button>
            </div>
          </div>
        </div>
      </main>
    </>
  )
}

export default StationDetails;