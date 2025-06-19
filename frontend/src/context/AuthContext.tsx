// import { jwtDecode, type JwtPayload } from "jwt-decode";
// import { createContext, useEffect, useState, type ReactNode } from "react";

// interface User{
//   id: string,
//   email: string,
//   name?: string,
// };

// interface AuthContextType {
//   isAuthenticated: boolean;
//   user: User | null;
//   loading: boolean;
//   login: (userData: User, token: string) => void;
//   logout: () => void;
// }

// const AuthContext = createContext<AuthContextType | undefined>(undefined);

// export const AuthProvider = ({children}: {children: ReactNode}) => {
//   const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
//   const [user, setUser] = useState<User | null>(null);
//   const [loading, setLoading] = useState<boolean>(true);

//   const decodeToken = (token: string): User | null => {
//     try{
//       const decoded = jwtDecode<JwtPayload>(token);
      
//     }
//   }

//   useEffect(() => {
//     const checkAuth = ():void => {
//       const token = localStorage.getItem('token');
//       if(!token){
//         setLoading(false);
//         return;
//       }


//     }
//   }, [])
// }