import axios from 'axios'

const baseUrl = '/api/v1/auth'

interface LoginCredentials {
  email: string,
  pin: string,
};

interface LoginResponse {
  token: string,
  tokenType: string,
};

const login = async (credentials: LoginCredentials): Promise<LoginResponse> => {
  try {
    const response = await axios.post<LoginResponse>(`${baseUrl}/login`, credentials)
    return response.data
  } catch (error: any) {
    if (error.response?.data) {
      throw new Error(error.response.data.error || 'Error logging in');
    }
    throw new Error('Connection problem');
  }
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
