import { useState } from 'react';
import type { ChargingStationCreate } from '../interfaces/ChargingStation';
import stationService from '../services/chargingStationService';

type Props = {
  onChargingStationAdded: () => void;
  setErrorMessage: (msg: string | null) => void;
  setTypeErrorMessage: (type: 'success' | 'error' | null) => void;
};

const AddChargingStationForm = ({ onChargingStationAdded, setErrorMessage, setTypeErrorMessage}: Props) => {
  const [form, setForm] = useState<ChargingStationCreate>({
    name: '',
    location: '',
    photoUrl: '',
    info: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!form.name.trim()) {
      setErrorMessage('El nombre es obligatorio');
      setTypeErrorMessage('error');
      return;
    }

    try {
      const newStation = await stationService.registerStation(form);
      setForm({ name: '', location: '', photoUrl: '', info: '' });
      setErrorMessage('Estación agregada correctamente');
      setTypeErrorMessage('success');
      setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
      onChargingStationAdded();
    } catch (err) {
      setErrorMessage('Error al agregar la estación');
      setTypeErrorMessage('error');
      setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <h2 className="text-lg font-semibold text-gray-800">Añadir Estación</h2>
      <div>
        <label htmlFor="name" className="block text-sm text-gray-700 mb-1 font-medium">
          Nombre *
        </label>
        <input
          type="text"
          name="name"
          id="name"
          value={form.name}
          onChange={handleChange}
          required
          className="w-full px-3 py-2 rounded-md border border-stone-300 bg-zinc-50 text-sm focus:outline-none focus:ring-2 focus:ring-cyan-700"
        />
      </div>

      <div>
        <label htmlFor="location" className="block text-sm text-gray-700 mb-1 font-medium">
          Ubicación
        </label>
        <input
          type="text"
          name="location"
          id="location"
          value={form.location || ''}
          onChange={handleChange}
          className="w-full px-3 py-2 rounded-md border border-stone-300 bg-zinc-50 text-sm focus:outline-none focus:ring-2 focus:ring-cyan-700"
        />
      </div>

      <div>
        <label htmlFor="photoUrl" className="block text-sm text-gray-700 mb-1 font-medium">
          URL de la foto
        </label>
        <input
          type="text"
          name="photoUrl"
          id="photoUrl"
          value={form.photoUrl || ''}
          onChange={handleChange}
          className="w-full px-3 py-2 rounded-md border border-stone-300 bg-zinc-50 text-sm focus:outline-none focus:ring-2 focus:ring-cyan-700"
        />
      </div>

      <div>
        <label htmlFor="info" className="block text-sm text-gray-700 mb-1 font-medium">
          Información adicional
        </label>
        <textarea
          name="info"
          id="info"
          rows={3}
          value={form.info || ''}
          onChange={handleChange}
          className="w-full px-3 py-2 rounded-md border border-stone-300 bg-zinc-50 text-sm focus:outline-none focus:ring-2 focus:ring-cyan-700"
        />
      </div>

      <button
        type="submit"
        className="w-full py-2 bg-cyan-700 text-white text-sm font-semibold rounded-md hover:bg-cyan-800 transition cursor-pointer"
      >
        Guardar estación
      </button>
    </form>
  );
};

export default AddChargingStationForm;