// import { useState } from 'react';
// import LoginForm from './components/LoginForm';
// import authService from './services/authService';
import { Route, Routes } from 'react-router';
import LoginPage from './pages/Login';
import Dashboard from './pages/Dashboard';

// type UserType = {
//   token: string;
//   tokenType: string;
// }

function App() {
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
      <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  )
}

export default App
