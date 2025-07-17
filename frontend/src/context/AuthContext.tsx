import { jwtDecode, type JwtPayload } from "jwt-decode";
import { createContext, useContext, useEffect, useState, type ReactNode } from "react";

interface User {
	id: string,
	email: string,
	name?: string,
};

interface AuthContextType {
	isAuthenticated: boolean;
	user: User | null;
	loading: boolean;
	login: (userData: User, token: string) => void;
	logout: () => void;
}

interface CustomJwtPayload extends JwtPayload {
	id: string,
	email: string,
	name?: string,
	roles?: string[],
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
	const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
	const [user, setUser] = useState<User | null>(null);
	const [loading, setLoading] = useState<boolean>(true);

	const decodeToken = (token: string): User | null => {
		try {
			const decoded = jwtDecode<CustomJwtPayload>(token);

			// Verificar si el token expiro
			if (decoded.exp && decoded.exp * 1000 < Date.now()) {
				return null;
			}

			return {
				id: decoded.id || '', // 'sub' as fallback, maybe bad idea(?)
				email: decoded.email || '',
				name: decoded.name,
			};
		} catch (error) {
			console.error('Error decoding token', error);
			return null;
		}
	}

	const login = (userData: User, token: string): void => {
		localStorage.setItem('token', token);
		localStorage.setItem('user', JSON.stringify(userData));
		setUser(userData);
		setIsAuthenticated(true);
	}

	const logout = (): void => {
		localStorage.removeItem('token');
		localStorage.removeItem('user');
		setUser(null);
		setIsAuthenticated(false);
	}

	useEffect(() => {
		const checkAuth = (): void => {
			const token = localStorage.getItem('token');
			if (!token) {
				setLoading(false);
				return;
			}

			const userData = decodeToken(token);
			if (userData) {
				setUser(userData);
				setIsAuthenticated(true);
			} else {
				localStorage.removeItem('token');
				localStorage.removeItem('user');
				setUser(null);
				setIsAuthenticated(false);
			}

			setLoading(false);
		}

		checkAuth();
	}, [])

	return (
		<AuthContext.Provider
			value={{
				isAuthenticated,
				user,
				loading,
				login,
				logout,
			}}
		>
			{children}
		</AuthContext.Provider>
	)
}

export const useAuth = (): AuthContextType => {
	const context = useContext(AuthContext);
	if (!context) {
		throw new Error('useAuth must be used within AuthProvider');
	}
	return context;
}