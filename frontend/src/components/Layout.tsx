import React, { useState } from "react";
import Sidebar from "./Sidebar";


const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [isSidebarExpanded, setIsSidebarExpanded] = useState(true);
  const marginLeftClass = isSidebarExpanded ? 'md:ml-64' : 'md:ml-20';
  return (
    <div className="flex">
      <Sidebar
        onToggle={(expanded: boolean) => setIsSidebarExpanded(expanded)} 
        isExpanded={isSidebarExpanded}
      />
      <main className={`${marginLeftClass} flex-1 p-10 transition-all duration-300`}>
        {children}
      </main>
    </div>
  );
};

export default Layout;
