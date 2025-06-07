import { useState } from 'react';
// import { useNavigate } from 'react-router';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    // const [error, setError] = useState('');
    // const navigate = useNavigate();

    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault();
        console.log(e);
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
                <label htmlFor="password" className="block text-sm text-gray-700 mb-1 font-medium">Pin</label>
                <input
                    id="password"
                    name="password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
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
