const DetallesCargador = ({ cargador } : {cargador: {direccion: string, tiempoCarga: number, estado: string, tiempoTotalCarga: number}}) => {
  return (
    <div className="charger-details">
      <h2>Cargador Eléctrico - Detalles</h2>
      <p><strong>Dirección:</strong> {cargador.direccion}</p>
      <p><strong>Tiempo de carga:</strong> {cargador.tiempoCarga} minutos</p>
      <p><strong>Estado:</strong> {cargador.estado}</p>

      <h3>Tiempo transcurrido en este cargador:</h3>
      <p><strong>Tiempo total de carga:</strong> {cargador.tiempoTotalCarga}</p>
    </div>
  );
}

export default DetallesCargador;
