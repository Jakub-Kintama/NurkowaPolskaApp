import React, { useEffect, useRef, useState } from "react";

export default function AddMarkerPopup(props) {
  const [coordinates, setCoordinates] = useState({ latitude: "", longitude: "" });
  const mapRef = useRef(null);

  useEffect(() => {
    const loadMap = () => {
      const mapOptions = {
        center: { lat: 51.927642227894296, lng: 16.495913836056648 },
        zoom: 8,
      };

      const map = new window.google.maps.Map(mapRef.current, mapOptions);

      const handleClick = (event) => {
        const lat = event.latLng.lat();
        const lng = event.latLng.lng();
        setCoordinates({ latitude: lat, longitude: lng });
      };

      map.addListener("click", handleClick);
    };

    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=YOUR_GOOGLE_MAPS_API_KEY&callback=loadMap`;
    script.async = true;
    script.defer = true;
    document.head.appendChild(script);

    return () => {
      document.head.removeChild(script);
    };
  }, []);

  return props.trigger ? (
    <div className="Popup">
      <div className="PopupInner">
        <div
          ref={mapRef}
          style={{ width: 600, height: 450 }}
        ></div>
        <button onClick={() => props.setTrigger(false)} className="CloseButton">
          Zamknij
        </button>
        <br />
        <h2>Dodaj</h2>
        <input type="text" placeholder="Latitude" value={coordinates.latitude} onChange={(e) => setCoordinates({ ...coordinates, latitude: e.target.value })} />
        <input type="text" placeholder="Longitude" value={coordinates.longitude} onChange={(e) => setCoordinates({ ...coordinates, longitude: e.target.value })} />
        <button className="SubmitButton">Submit</button>
      </div>
    </div>
  ) : "";
}