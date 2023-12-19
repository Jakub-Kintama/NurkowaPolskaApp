import React, { useState } from 'react';
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export default function AddMarkerPopupFN(props) {
  const [lat, setLat] = useState("");
  const [lng, setLng] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [date, setDate] = useState(new Date());
  const [crayfishType, setCrayfishType] = useState("");

  const handleLatChange = (e) => {
    setLat(e.target.value);
  };

  const handleLngChange = (e) => {
    setLng(e.target.value);
  };

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  const handleDateChange = (date) => {
    setDate(date);
  };

  const handleCrayfishTypeChange = (e) => {
    setCrayfishType(e.target.value);
  };

  const handleSubmit = async () => {
    try {
      const data = {
        mapMarker: {
            position: {
                lat: lat,
                lng: lng
            },
            title: title,
            description: description            
        },
        userEmail: props.email,
        CrayfishType: crayfishType,
        date: date.toISOString().split('T')[0],
        verified: false
      };
      const config = {
        headers: {
          'Authorization': `Bearer ${props.token}`,
        },
      };

      await axios.post("http://172.19.100.10:8080/api/markers", data, config);
      props.setTrigger(false);
      props.refreshTable(true);
      setLat("");
      setLng("");
      setTitle("");
      setDescription("");
      setDate(new Date());
      setCrayfishType("");

    } catch (error) {
      console.error("Błąd podczas przesyłania danych:", error);
    }
  };

  return props.trigger ? (
    <div className="Popup">
      <div className="PopupInner">
        <button onClick={() => props.setTrigger(false)} className="CloseButton">Zamknij</button>
        <br/>
        <h2>Dodaj Znacznik</h2>
        <input
          type="text"
          placeholder="Latitude"
          value={lat}
          onChange={handleLatChange}
        /><br/>
        <input
          type="text"
          placeholder="Longitude"
          value={lng}
          onChange={handleLngChange}
        /><br/>
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={handleTitleChange}
        /><br/>
        <textarea
          placeholder="Description"
          value={description}
          onChange={handleDescriptionChange}
        /><br/>
        <DatePicker
          selected={date}
          onChange={handleDateChange}
          dateFormat="yyyy-MM-dd"
        /><br/>
        <select value={crayfishType} onChange={handleCrayfishTypeChange}>
          <option value="">Wybierz typ raka</option>
          <option value="SIGNAL">Sygnałowy</option>
          <option value="AMERICAN">Amerykański</option>
          <option value="NOBLE">Szlachetny</option>
          <option value="GALICIAN">Galicyjski</option>
        </select>
        <button onClick={handleSubmit}>Submit</button>
      </div>
    </div>
  ) : "";
}