import { Link } from "react-router";

function Home() {
  return (
    <div>
      <h1>Bienvenido a la App de Cargadores</h1>
      <nav>
        <ul>
          <li><Link to="/registro">Registrarse</Link></li>
          <li><Link to="/mapa">Ver Cargadores</Link></li>
          <li><Link to="/tarjetas">Registrar Tarjeta</Link></li>
        </ul>
      </nav>
    </div>
  );
}

export default Home;
