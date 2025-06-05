import { GoogleMap, LoadScript, Marker } from '@react-google-maps/api';

const MapPage = () => {
  const mapContainerStyle = {
    width: '100%',
    height: '400px',
  };

  const center = {
    lat: -36.8317,
    lng: -73.0507,
  };

  const markers = [
    { lat: -36.8317, lng: -73.0507 },
  ];

  return (
    //TODO key api
    <LoadScript googleMapsApiKey="YOUR_GOOGLE_MAPS_API_KEY">
      <GoogleMap
        mapContainerStyle={mapContainerStyle}
        center={center}
        zoom={15}
      >
        {markers.map((marker, index) => (
          <Marker key={index} position={marker} />
        ))}
      </GoogleMap>
    </LoadScript>
  );
}

export default MapPage;
