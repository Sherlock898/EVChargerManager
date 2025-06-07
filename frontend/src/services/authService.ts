import axios from 'axios'

const baseUrl = '/api/v1/auth'

interface LoginCredentials {
  email: string
  pin: string
}

const login = async (credentials: LoginCredentials) => {
  const response = await axios.post(`${baseUrl}/login`, credentials)
  return response.data
}

// async function login2(username, password) {
//     try {
//         const response = await axios.post('http://localhost:8080/api/v1/auth/login', { username, password });
//         return response.data.token;
//     } catch (error) {
//         console.error('Error during login:', error);
//         throw error;
//     }
// }

export default { login }
