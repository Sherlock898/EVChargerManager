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

export default { login }
