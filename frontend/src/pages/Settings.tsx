import React, { useEffect, useState } from 'react';
import axios from 'axios';

type UserData = {
  firstName: string;
  lastName: string;
  email: string;
  organization?: string;
};

type Props = {
  user: { token: string; tokenType: string };
  onLogout: () => void;
  theme: 'light' | 'dark';
  onThemeChange: (theme: 'light' | 'dark') => void;
};

const Settings = ({ user, onLogout, theme, onThemeChange }: Props) => {
  const [userData, setUserData] = useState<UserData | null>(null);

  // Estados para cambiar email
  const [newEmail, setNewEmail] = useState('');
  const [currentPinForEmail, setCurrentPinForEmail] = useState('');
  const [emailMessage, setEmailMessage] = useState<{ text: string; type: 'error' | 'success' } | null>(null);

  // Estados para cambiar pin
  const [currentPin, setCurrentPin] = useState('');
  const [newPin, setNewPin] = useState('');
  const [pinMessage, setPinMessage] = useState<{ text: string; type: 'error' | 'success' } | null>(null);

  // Cargar datos usuario
  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get('/api/v1/users/me', {
          headers: {
            Authorization: `${user.tokenType} ${user.token}`,
          },
        });
        setUserData(response.data);
        setNewEmail(response.data.email);
      } catch (error) {
        console.error('Error al cargar datos de usuario', error);
      }
    };
    fetchUser();
  }, [user]);

  // Cambiar email
  const handleEmailChange = async (e: React.FormEvent) => {
    e.preventDefault();
    setEmailMessage(null);
    try {
      await axios.put(
        '/api/v1/users/email',
        { newEmail, currentPin: currentPinForEmail },
        {
          headers: { Authorization: `${user.tokenType} ${user.token}` },
        }
      );
      setEmailMessage({ text: 'Correo actualizado correctamente', type: 'success' });
      setCurrentPinForEmail('');
    } catch (err: any) {
      const msg = err.response?.data || 'Error al actualizar correo';
      setEmailMessage({ text: msg, type: 'error' });
    }
  };

  // Cambiar pin
  const handlePinChange = async (e: React.FormEvent) => {
    e.preventDefault();
    setPinMessage(null);
    try {
      await axios.put(
        '/api/v1/users/pin',
        { currentPin, newPin },
        {
          headers: { Authorization: `${user.tokenType} ${user.token}` },
        }
      );
      setPinMessage({ text: 'PIN actualizado correctamente', type: 'success' });
      setCurrentPin('');
      setNewPin('');
    } catch (err: any) {
      const msg = err.response?.data || 'Error al actualizar PIN';
      setPinMessage({ text: msg, type: 'error' });
    }
  };

  // Toggle tema claro/oscuro
  const toggleTheme = () => {
    const nextTheme = theme === 'light' ? 'dark' : 'light';
    onThemeChange(nextTheme);
  };

  if (!userData) return <p>Cargando...</p>;

  return (
    <div className="max-w-xl mx-auto space-y-8">
      <h1 className="text-2xl font-semibold mb-4">Configuraciones</h1>

      <section>
        <h2 className="text-xl font-medium mb-2">Datos del usuario</h2>
        <p>
          Nombre: {userData.firstName} {userData.lastName}
        </p>
        <p>Email: {userData.email}</p>
        {userData.organization && <p>Organización: {userData.organization}</p>}
      </section>

      <section>
        <h2 className="text-xl font-medium mb-2">Cambiar correo electrónico</h2>
        <form onSubmit={handleEmailChange} className="space-y-2">
          <input
            type="email"
            value={newEmail}
            onChange={(e) => setNewEmail(e.target.value)}
            placeholder="Nuevo correo"
            required
            className="w-full border rounded px-3 py-2"
          />
          <input
            type="password"
            value={currentPinForEmail}
            onChange={(e) => setCurrentPinForEmail(e.target.value)}
            placeholder="PIN actual"
            required
            className="w-full border rounded px-3 py-2"
          />
          <button type="submit" className="px-4 py-2 bg-cyan-700 text-white rounded hover:bg-cyan-800">
            Cambiar correo
          </button>
          {emailMessage && (
            <p className={`mt-1 text-sm ${emailMessage.type === 'error' ? 'text-red-600' : 'text-green-600'}`}>
              {emailMessage.text}
            </p>
          )}
        </form>
      </section>

      <section>
        <h2 className="text-xl font-medium mb-2">Cambiar PIN</h2>
        <form onSubmit={handlePinChange} className="space-y-2">
          <input
            type="password"
            value={currentPin}
            onChange={(e) => setCurrentPin(e.target.value)}
            placeholder="PIN actual"
            required
            className="w-full border rounded px-3 py-2"
          />
          <input
            type="password"
            value={newPin}
            onChange={(e) => setNewPin(e.target.value)}
            placeholder="Nuevo PIN"
            required
            className="w-full border rounded px-3 py-2"
          />
          <button type="submit" className="px-4 py-2 bg-cyan-700 text-white rounded hover:bg-cyan-800">
            Cambiar PIN
          </button>
          {pinMessage && (
            <p className={`mt-1 text-sm ${pinMessage.type === 'error' ? 'text-red-600' : 'text-green-600'}`}>
              {pinMessage.text}
            </p>
          )}
        </form>
      </section>

      <section>
        <h2 className="text-xl font-medium mb-2">Tema</h2>
        <button
          onClick={toggleTheme}
          className="px-4 py-2 rounded bg-gray-300 dark:bg-gray-700 hover:bg-gray-400 dark:hover:bg-gray-600"
        >
          Cambiar a {theme === 'light' ? 'oscuro' : 'claro'}
        </button>
      </section>

      <section>
        <h2 className="text-xl font-medium mb-2">Cerrar sesión</h2>
        <button
          onClick={onLogout}
          className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
        >
          Cerrar sesión
        </button>
      </section>
    </div>
  );
};

export default Settings;
