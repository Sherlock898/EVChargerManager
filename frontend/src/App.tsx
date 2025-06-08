// import { useState } from 'react';
// import LoginForm from './components/LoginForm';
// import authService from './services/authService';
import { Route, Routes } from 'react-router';
import Layout from "./components/Layout";
import Dashboard from './pages/Dashboard';
import LoginPage from './pages/Login';
import StationDetails from './pages/StationDetails';
import Stations from './pages/Stations';

// type UserType = {
//   token: string;
//   tokenType: string;
// }

const App = () => {
  // const [email, setEmail] = useState<string>('');
  // const [pin, setPin] = useState<string>('');
  // const [user, setUser] = useState<UserType | null>(null)
  // const [errorMessage, setErrorMessage] = useState<string | null>(null)
  // const [typeErrorMessage, setTypeErrorMessage] = useState<string | null>(null)

  // const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
  //   event.preventDefault()

  //   try {
  //     const loggedUser = await authService.login({email, pin})
  //     setUser(loggedUser)
  //     setEmail('')
  //     setPin('')
  //   } catch (exception) {
  //     setErrorMessage('Wrong credentials')
  //     setTypeErrorMessage('error')
  //     setTimeout(() => {
  //       setErrorMessage(null)
  //       setTypeErrorMessage(null)
  //     }, 5000)
  //   }
  // }

  // console.log(user)

  return (
    <Routes >
      <Route path="/login" element={<LoginPage />} />
      <Route
        path="/dashboard"
        element={
          <Layout>
            <Dashboard />
          </Layout>
        }
      />
      <Route
        path="/stations"
        element={
          <Layout>
            <Stations />
          </Layout>
        }
      />
      <Route path="/stations/:stationId" element={<StationDetails />} />
    </Routes>
  );
};

export default App
