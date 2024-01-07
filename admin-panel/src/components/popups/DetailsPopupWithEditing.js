import React, { useState } from "react";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { baseURL } from "../functions";

export default function DetailsPopupWithEditing(props) {
    const [role] = useState(props.role);

    const [date, setDate] = useState(new Date(props.marker.date));
    const [crayfishType, setCrayfishType] = useState(props.marker.crayfishType);
    const [title, setTitle] = useState(props.marker.title);
    const [description, setDescription] = useState(props.marker.description);
    const [verified, setVerified] = useState(props.marker.verified);

    const handleClose = () => {
        props.setTrigger(false);
    }

    const handleDateChange = (date) => {
        setDate(date);
    };

    const handleCrayfishTypeChange = (e) => {
        setCrayfishType(e.target.value);
    };

    const handleTitleChange = (e) => {
        setTitle(e.target.value);
    };

    const handleDescriptionChange = (e) => {
        setDescription(e.target.value);
    };

    const handleDelete = async () => {
        const config = {
            headers: {
              'Authorization': `Bearer ${props.token}`,
            },
        };
        if (role === "ADMIN") {
            try {
                await axios.delete(`${baseURL}/api/admin/markers/${props.marker.id}`, config);
            } catch (error) {
                console.error("Błąd podczas usuwania rekordu", error);
            }
        } else {
            try {
                await axios.delete(`${baseURL}/api/markers/${props.marker.id}`, config);
            } catch (error) {
                console.error("Błąd podczas usuwania rekordu", error);
            }
        }
        props.setTrigger(false);
        props.refreshTable(true);
       
    };
    
    const handleSubmit = async () => {
        const data = {
            _id: props.marker.id,
            mapMarker: {
                position: {
                    lat: props.marker.lat,
                    lng: props.marker.lng
                },
                title: title,
                description: description            
            },
            userEmail: props.marker.userEmail,
            CrayfishType: crayfishType,
            date: date.toISOString().split('T')[0],
            verified: verified
        };
        const config = {
            headers: {
              'Authorization': `Bearer ${props.token}`,
            },
        };
        if (role === "ADMIN") {
            try {
                await axios.patch(`${baseURL}/api/admin/markers`, data, config);
    
            } catch (error) {
                console.error("Błąd podczas patchowania", error);
            }
        } else {
            try {
                await axios.patch(`${baseURL}/api/markers`, data, config);
            } catch (error) {
                console.error("Błąd podczas patchowania", error);
            }
        }
        props.setTrigger(false);
        props.refreshTable(true);
    };
    const handleStatusChange = async () => {
        try {
            const data = {
                _id: props.marker.id,
                mapMarker: {
                    position: {
                        lat: props.marker.lat,
                        lng: props.marker.lng
                    },
                    title: props.marker.title,
                    description: props.marker.description            
                },
                userEmail: props.marker.userEmail,
                CrayfishType: props.marker.crayfishType,
                date: props.marker.date,
                verified: !props.marker.verified
            };
            const config = {
                headers: {
                  'Authorization': `Bearer ${props.token}`,
                },
            };
            await axios.patch(`${baseURL}/api/admin/markers`, data, config);
            // props.setTrigger(false);
            setVerified(!verified);
            props.refreshTable(true);
        } catch (error) {
            console.error("Błąd podczas patchowania", error);
        }
    };

    return (props.trigger) ? (
        <div className="DetailsPopup">
            <div className="DetailsPopupInner">
                <button onClick={ () => handleClose() } className="CloseButton">Zamknij</button>
                <h2>Znacznik</h2>
                <div className="DetailsPopupContent">
                    <iframe className="DetailsIframe" title={title} src={`https://maps.google.com/maps?q=${props.marker.lat},${props.marker.lng}&z=14&output=embed`} allowFullScreen="" loading="lazy" referrerPolicy="no-referrer-when-downgrade"></iframe>
                    <div className="Details">
                        <label htmlFor="dpicker"><strong>Data dodania: </strong></label>
                            <DatePicker
                                id="dpicker"
                                selected={date}
                                onChange={handleDateChange}
                                dateFormat="yyyy-MM-dd"
                            />
                        
                        <p><strong>Typ: </strong>
                            <select value={crayfishType} onChange={handleCrayfishTypeChange}>
                              <option value="SIGNAL">Sygnałowy</option>
                              <option value="AMERICAN">Amerykański</option>
                              <option value="NOBLE">Szlachetny</option>
                              <option value="GALICIAN">Galicyjski</option>
                              <option value="OTHER">Pozostałe</option>
                            </select>
                        </p>
                        <p><strong>Tytuł: </strong><input type="text" value={title} onChange={handleTitleChange}/></p>
                        <p><strong>Opis: </strong><textarea value={description} onChange={handleDescriptionChange}/></p>
                        {role === "ADMIN" ? 
                            <p><strong>Status: </strong><button onClick={handleStatusChange}>{verified === true ? "Zweryfikowany" : "Niezweryfikowany" }</button></p>
                            : <p><strong>Status: </strong>{verified === true ? "Zweryfikowany" : "Niezweryfikowany" }</p>
                        }
                        <p><strong>Dodane przez: </strong>{props.marker.userEmail}</p>
                        <button onClick={handleSubmit}>Prześlij zmiany</button>
                    </div>
                </div>
                <button onClick={handleDelete} className="DeleteButton">
                    <span role="img" aria-label="Delete">🗑️</span> Usuń
                </button>
            </div>
        </div>
    ) : "";
}