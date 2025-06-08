import { useEffect, useState } from "react";
import { Link } from "react-router";
import AddChargingStationForm from "../components/AddChargingStationForm";
import type { ChargingStation } from '../interfaces/ChargingStation';
import stationService from '../services/chargingStationService';

const Stations = () => {
  const [stations, setStations] = useState<ChargingStation[]>([]);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [typeErrorMessage, setTypeErrorMessage] = useState<'success' | 'error' | null>(null);
  const [showForm, setShowForm] = useState(false);

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
  
  const handleStationAdded = async () => {
    try {
      const updateStations = await stationService.getUserStations();
      setStations(updateStations);
      setErrorMessage("Estación agregada exitosamente.");
      setTypeErrorMessage("success");
      setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
      } catch(err){
        console.error(err);
        setErrorMessage("Error al actualizar estaciones.");
        setTypeErrorMessage("error");
        setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
    }
  }
  
  return (
    <div className="flex-1 p-10 min-h-screen bg-gray-50">
       {showForm && (
        <div className="fixed inset-0 bg-black/30 backdrop-blur-sm flex items-center justify-center z-50">
          <div className="bg-white rounded-md p-6 shadow-lg max-w-lg w-full relative z-50">
            <button
              className="absolute top-2 right-2 text-gray-500 hover:text-gray-700"
              onClick={() => setShowForm(false)}
            >
              ✕
            </button>
            <AddChargingStationForm
              onChargingStationAdded={() => {
                handleStationAdded();
                setShowForm(false);
              }}
              setErrorMessage={setErrorMessage}
              setTypeErrorMessage={setTypeErrorMessage}
            />
          </div>
        </div>
      )}

      <div className="bg-white p-8 rounded-md shadow-md">
        <h1 className="text-2xl font-light text-gray-800 mb-6 text-center">Mis Estaciones de Carga</h1>

        {errorMessage && (
          <div
            className={`text-sm mb-6 px-4 py-2 rounded text-center font-medium ${
              typeErrorMessage === "success"
                ? "bg-green-100 text-green-800"
                : "bg-red-100 text-red-800"
            }`}
          >
            {errorMessage}
          </div>
        )}

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {stations.map((station) => (
            <Link to={`/stations/${station.id}`} state={{station}} >
              <div
                key={station.id}
                className="bg-gray-50 p-4 rounded shadow border border-gray-200 flex flex-col items-center cursor-pointer"
              >
                {station.photoURL ? (
                  <img
                    src={station.photoURL}
                    alt={`Estación ${station.name}`}
                    className="w-24 h-24 object-cover rounded-full mb-3"
                  />
                ) : (
                  <div className="w-24 h-24 rounded-full bg-gray-300 mb-3 flex items-center justify-center text-gray-600">
                    Sin imagen
                  </div>
                )}
                <h2 className="text-lg font-semibold text-gray-800">{station.name}</h2>
                {station.location && <p className="text-sm text-gray-600">📍 {station.location}</p>}
                {station.info && <p className="text-sm text-gray-600 mt-1">ℹ️ {station.info}</p>}
              </div>
            </Link>
          ))}
        </div>

        <div className="mt-10 flex justify-center">
          <button
            onClick={() => setShowForm(true)}
            className="px-6 py-2 bg-cyan-700 text-white rounded-md hover:bg-cyan-800 transition"
          >
            + Añadir Estación
          </button>
        </div>
      </div>
    </div>
  );
};

export default Stations;