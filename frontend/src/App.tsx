// import LoginForm from './components/LoginForm';
// import authService from './services/authService';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router';
import Layout from "./components/Layout";
import Dashboard from './pages/Dashboard';
import LoginPage from './pages/Login';
import StationDetails from './pages/StationDetails';
import Stations from './pages/Stations';
import Settings from './pages/Settings';
import React, { useEffect, useState } from 'react';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';

type UserType = {
  token: string;
  tokenType: string;
};

const AppContent: React.FC = () => {
  const [user, setUser] = useState<UserType | null>(
    JSON.parse(localStorage.getItem('user') || 'null')
  );
  const [theme, setTheme] = useState<'light' | 'dark'>(
    localStorage.getItem('theme') === 'dark' ? 'dark' : 'light'
  );

  useEffect(() => {
    document.documentElement.classList.toggle('dark', theme === 'dark');
    localStorage.setItem('theme', theme);
  }, [theme]);

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  return (
    <Routes >
      <Route path="/login" element={<LoginPage />} />

      <Route path="/" element={<Navigate to="/dashboard" replace />} />

      <Route element={<ProtectedRoute />}>
        <Route
          path="/dashboard"
          element={
            <Layout>
              <Dashboard />
            </Layout>
          }
        />

        <Route
          path="/estaciones"
          element={
            <Layout>
              <Stations />
            </Layout>
          }
        />
        <Route
          path="/stations/:stationId"
          element={
            <Layout>
              <StationDetails />
            </Layout>
          }
        />
        <Route
          path="/settings"
          element={
            user ? (
              <Layout>
                <Settings
                  user={user}
                  onLogout={handleLogout}
                  theme={theme}
                  onThemeChange={setTheme}
                />
              </Layout>
            ) : (
              <Navigate to="/login" />
            )
          }
        />
      </Route>
    </Routes>
  );
};

const App: React.FC = () => {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppContent />
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
