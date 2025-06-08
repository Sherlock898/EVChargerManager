import { useLocation, useParams } from "react-router";
import Sidebar from "../components/Sidebar";
import type { ChargingStation } from "../interfaces/ChargingStation";

const StationDetails = () => {
  const { stationId } = useParams<{ stationId: string }>();
  const location = useLocation()
  const station = (location.state as { station: ChargingStation })?.station

  if (!station) return <p>No se pudo cargar información de la estación</p>

  return (
    <>
      <Sidebar />
      <main className="p-6 flex-1">
        <h2 className="text-2xl font-bold mb-6">{station.name}</h2>
        <div className="flex flex-col md:flex-row gap-8">
          <div className="flex-1">
            <div className="bg-white rounded-full overflow-hidden w-48 h-48 flex items-center justify-center mb-6">
              <img src={station.photoURL} alt={station.name} className="w-full h-full object-cover" />
            </div>
            <p className="mb-2 text-gray-700">{station.info}</p>
            <p className="text-gray-700">{station.location}</p>
          </div>
          <div className="flex-1">
            <h3 className="text-xl font-semibold border-b pb-2 mb-4">Lista de cargadores</h3>
            <ul className="space-y-4">
              {station.chargers.map((charger, index) => (
                <li key={index} className="flex items-center gap-2">
                  <span className="font-medium">• Nombre cargador {index + 1}</span> 
                  <span className="w-4 h-4 bg-blue-200 rounded-full"></span>
                  {charger.chargerStatus && <span className="text-gray-600">({charger.chargerStatus})</span>}
                </li>
              ))}
            </ul>
            <button className="mt-8 border-2 border-black px-4 py-2 flex items-center gap-2">
              <span className="text-2xl font-bold">+</span> Añadir cargador
            </button>
          </div>
        </div>
      </main>
    </>
  )
}

export default StationDetails;