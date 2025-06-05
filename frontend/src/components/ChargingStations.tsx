import { useEffect, useState } from 'react';
import AddChargingStationForm from './AddChargingStationForm';

type ChargingStation = {
  id: number;
  name: string;
  location?: string;
  photoUrl?: string;
  info?: string;
};

type Props = {
  token: string;
  setErrorMessage: (msg: string | null) => void;
  setTypeErrorMessage: (type: 'success' | 'error' | null) => void;
};


const ChargingStations = ({ token, setErrorMessage, setTypeErrorMessage }: Props) => {
  const [chargingStations, setChargingStations] = useState<ChargingStation[]>([]);

  const fetchChargingStations = async () => {
    const response = await fetch('/api/v1/charging-stations', {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });
    const data = await response.json();
    setChargingStations(data);
  };

  useEffect(() => {
    fetchChargingStations();
  }, []);

  return (
    <div>
      <AddChargingStationForm token={token} onChargingStationAdded={fetchChargingStations} setErrorMessage={setErrorMessage} setTypeErrorMessage={setTypeErrorMessage} />
      <h2>Estaciones</h2>
      <ul>
        {chargingStations.map(chargingStation => (
          <li key={chargingStation.id}>
            <strong>{chargingStation.name}</strong><br />
            {chargingStation.location && <span>📍 {chargingStation.location}<br /></span>}
            {chargingStation.photoUrl && <img src={chargingStation.photoUrl} alt={chargingStation.name} width={100} />}<br />
            {chargingStation.info && <p>{chargingStation.info}</p>}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ChargingStations;
