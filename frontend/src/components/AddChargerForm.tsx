import { useState } from 'react';
import type { ChargerCreate } from '../interfaces/Charger'; // Define esta interfaz si no existe
import chargerService from '../services/chargerService'; // Crea este servicio si aún no existe

type Props = {
  stationId: string;
  onChargerAdded: (name: string) => void;
  setErrorMessage: (msg: string | null) => void;
  setTypeErrorMessage: (type: 'success' | 'error' | null) => void;
};

const AddChargerForm = ({ stationId, onChargerAdded, setErrorMessage, setTypeErrorMessage }: Props) => {
  const [form, setForm] = useState<ChargerCreate>({ name: '' });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!form.name.trim()) {
      setErrorMessage('El nombre del cargador es obligatorio');
      setTypeErrorMessage('error');
      return;
    }

    try {
      setForm({ name: '' });
      setErrorMessage('Cargador agregado correctamente');
      setTypeErrorMessage('success');
      setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
      await onChargerAdded(form.name);
    } catch (err) {
      setErrorMessage('Error al agregar el cargador');
      setTypeErrorMessage('error');
      setTimeout(() => {
        setErrorMessage(null);
        setTypeErrorMessage(null);
      }, 5000);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <h2 className="text-lg font-semibold text-gray-800">Añadir Cargador</h2>
      <div>
        <label htmlFor="name" className="block text-sm text-gray-700 mb-1 font-medium">
          Nombre del cargador *
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
      <button
        type="submit"
        className="w-full py-2 bg-cyan-700 text-white text-sm font-semibold rounded-md hover:bg-cyan-800 transition"
      >
        Guardar cargador
      </button>
    </form>
  );
};

export default AddChargerForm;
