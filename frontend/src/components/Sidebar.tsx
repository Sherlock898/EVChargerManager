import axios from 'axios';
import { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router';

interface NavItem {
  name: string;
  path: string;
  icon?: React.ReactNode;
}

const navItems: NavItem[] = [
  { name: 'Estaciones', path: '/estaciones' },
  { name: 'Mapa general de cargadores', path: '/mapa' },
  { name: 'Información de gastos', path: '/gastos' },
  { name: 'Usuarios', path: '/usuarios' },
  { name: 'Configuraciones', path: '/configuraciones' },
];

type UserPanelProps = {
  user?: {
    token: string;
    tokenType: string;
  };
  className?: string
};

type UserData = {
  name: string;
  organization: string;
  photoUrl?: string;
};

const UserPanel = ({ user, className }: UserPanelProps) => {
  const [userData, setUserData] = useState<UserData | null>(null);

  useEffect(() => {
    if (user) {
      const fetchUserData = async () => {
        try {
          const response = await axios.get('/api/v1/users/me', {
            headers: {
              Authorization: `${user.tokenType} ${user.token}`,
            },
          });
          setUserData(response.data);
        } catch (error) {
          console.error('Error fetching user data', error);
        }
      };
      fetchUserData();
    }
  }, [user]);

  if (!userData) {
    setUserData({
      photoUrl: 'https://avatars.githubusercontent.com/u/114520191?v=4',
      name: 'Nombre de ejemplo',
      organization: 'Organizacion'
    })
    return null;
  };

  return (
    <div className={`px-6 pb-6 flex items-center gap-3 ${className ? className : ''}`}>
      <img src={userData.photoUrl || '/default_avatar.png'} alt="Avatar" className="w-10 h-10 bg-gray-200 rounded-full flex-shrink-0" />
      <div className="flex flex-col">
        <strong className='font-medium text-white'>{userData.name}</strong>
        <p className='text-sm text-gray-300'>{userData.organization}</p>
      </div>
    </div>
  )
}

const Sidebar = () => {
  const location = useLocation();
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  return (
    <>
      {/* Botón de hamburguesa móvil - solo visible en móvil */}
      <div className="md:hidden fixed top-0 left-0 w-full bg-[#2c3e50] p-2 flex justify-between items-center z-1000">
        <div className="text-white font-medium">EVMS</div>
        <button
          onClick={toggleMobileMenu}
          className="p-2 text-white cursor-pointer"
        >
          {/* Icono de hamburguesa simple con CSS */}
          <div className="w-6 h-5 flex flex-col justify-between">
            <span className="w-full h-0.5 bg-white rounded-sm"></span>
            <span className="w-full h-0.5 bg-white rounded-sm"></span>
            <span className="w-full h-0.5 bg-white rounded-sm"></span>
          </div>
        </button>
      </div>

      {/* Sidebar*/}
      <aside
        className={`
          bg-[#2c3e50] text-white border-r border-[#1a2533]
          
          /* Mobile: open and close with button */
          fixed top-0 left-0 z-20 flex flex-col
          ${isMobileMenuOpen ? 'translate-x-0' : '-translate-x-full'} 
          w-full h-full pt-16
          
          /* Desktop: Always vissible */
          md:translate-x-0 md:w-64 md:pt-6 
          
          transition-transform duration-300 ease-in-out
        `}
      >
        {/* User panel */}
        <UserPanel className='border-b border-[#3d5166]' />

        {/* Items */}
        <nav className="mt-5 flex-1">
          <ul className="space-y-2 px-3 md:space-y-2">
            {navItems.map((item) => (
              <li key={item.path}>
                <Link
                  to={item.path}
                  onClick={() => setIsMobileMenuOpen(false)} // Close menu on clock
                  className={`flex items-center gap-2 px-3 py-3 md:py-2.5 rounded-md ${location.pathname === item.path
                      ? 'bg-[#1a2533] text-white'
                      : 'text-gray-300 hover:bg-[#3d5166] hover:text-white'
                    }`}
                >
                  <div className="w-5 h-5 flex items-center justify-center">
                    <div className={`w-4 h-4 rounded-full border border-current ${location.pathname === item.path ?
                        'border-white bg-[#3498db]' : 'border-gray-300 bg-transparent'
                      }`}></div>
                  </div>
                  <span className="text-base md:text-sm">{item.name}</span>
                </Link>
              </li>
            ))}
          </ul>
        </nav>

        {/* Logout */}
        <div className="mt-auto p-3 border-t border-[#3d5166]">
          <button className="w-full flex items-center gap-2 px-3 py-3 md:py-2.5 text-gray-300 hover:bg-[#1a2533] rounded-md cursor-pointer">
            <div className="w-5 h-5 flex items-center justify-center">
              <div className="w-4 h-4 rounded-full border border-gray-300"></div>
            </div>
            <span>Cerrar sesión</span>
          </button>
        </div>
      </aside>
    </>
  );
};


export default Sidebar;
