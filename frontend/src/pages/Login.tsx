import { useState } from 'react';
import { useNavigate } from 'react-router';
import { useAuth } from '../context/AuthContext';
import authService from '../services/authService';

interface LoginResponse {
    token: string,
    tokenType: string
};

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [pin, setPin] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const navigate = useNavigate();
    const { login } = useAuth();

    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault();
        setError('');
        setIsLoading(true);
        // TODO: Move this to auth service
        try {
            const response = await authService.login({email, pin});
            const userData = {
                id: email,
                email: email,
                name: undefined
            };

            login(userData, response.token);

            navigate('/estaciones');
        } catch (error: any) {
            console.error(error.message);
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <main className="relative min-h-screen flex items-center justify-center bg-gradient-to-br from-[#003f4d] via-[#005f73] to-[#007595]">
            <form onSubmit={handleSubmit} className="bg-white p-8 rounded-md border border-zinc-200 space-y-6 w-full max-w-sm">
                <h1 className="text-xl font-light text-gray-800 text-center tracking-wide">EVMS Login</h1>

                <div>
                    <label htmlFor="email" className="block text-sm text-gray-700 mb-1 font-medium">Email</label>
                    <input
                        id="email"
                        name="email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="w-full px-3 py-2 rounded-md border border-stone-300 bg-zinc-50 text-sm focus:outline-none focus:ring-2 focus:ring-cyan-700"
                    />
                </div>

                <div>
                    <label htmlFor="pin" className="block text-sm text-gray-700 mb-1 font-medium">Pin</label>
                    <input
                        id="pin"
                        name="pin"
                        type="password"
                        value={pin}
                        onChange={(e) => setPin(e.target.value)}
                        className="w-full px-3 py-2 rounded-md border border-stone-300 bg-zinc-50 text-sm focus:outline-none focus:ring-2 focus:ring-cyan-700"
                    />
                </div>

                <button
                    type="submit"
                    className="w-full py-2 bg-cyan-700 text-white text-sm font-semibold rounded-md hover:bg-cyan-800 transition cursor-pointer"
                >
                    Iniciar Sesión
                </button>
            </form>
        </main>
    )
}

export default LoginPage;
