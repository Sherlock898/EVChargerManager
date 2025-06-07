import { useEffect, useState } from 'react';
import api from '../api/apiClient';

type ChargerType = {
    id: string,
    name: string
}

const NearbyChargers = () => {
    const [chargers, setChargers] = useState<ChargerType[]>([]);

    useEffect(() => {
        async function fetchNearbyChargers() {
            try {
                const response = await api.get('/chargers/nearby');
                setChargers(response.data);
            } catch (error) {
                console.error('Error fetching nearby chargers:', error);
            }
        }

        fetchNearbyChargers();
    }, []);

    return (
        <div>
            <h1>Cargadores Cercanos</h1>
            <ul>
                {chargers.map((charger) => (
                    <li key={charger.id}>{charger.name}</li>
                ))}
            </ul>
        </div>
    );
}

export default NearbyChargers;
