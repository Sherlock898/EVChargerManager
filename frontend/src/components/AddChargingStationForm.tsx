import { useState } from 'react';

type ChargingStationData = {
  name: string;
  location?: string;
  photoUrl?: string;
  info?: string;
};

type Props = {
  onChargingStationAdded: () => void;
  token: string;
  setErrorMessage: (msg: string | null) => void;
  setTypeErrorMessage: (type: 'success' | 'error' | null) => void;
};

const AddChargingStationForm = ({ onChargingStationAdded, token, setErrorMessage, setTypeErrorMessage}: Props) => {
  const [form, setForm] = useState<ChargingStationData>({ name: ''});

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
      const response = await fetch('/api/v1/charging-stations', {
        method: 'POST',
        headers: {
          'Content-Type': 'aplication*json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(form),
      });

      if (!response.ok) throw new Error();

      setForm({ name: ''});
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
    <form onSubmit={handleSubmit} className="add-station-form">
      <h2>Añadir Estación</h2>
      <input type="text" name="name" placeholder="Nombre *" value={form.name} onChange={handleChange} required />
      <input type="text" name="location" placeholder="Ubicación" value={form.location || ''} onChange={handleChange} />
      <input type="text" name="photoUrl" placeholder="URL de la foto" value={form.photoUrl || ''} onChange={handleChange} />
      <textarea name="info" placeholder="Información adicional" value={form.info || ''} onChange={handleChange} />
      <button type="submit">Guardar estación</button>
    </form>
  );
};

export default AddChargingStationForm;