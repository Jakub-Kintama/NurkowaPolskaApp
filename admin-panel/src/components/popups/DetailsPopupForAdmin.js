import React, { useState } from "react";
import axios from "axios";

export default function DetailsPopupForAdmin(props) {

    const [date, setDate] = useState(props.marker.date);
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
        try {
            const config = {
                headers: {
                  'Authorization': `Bearer ${props.token}`,
                },
            };
            await axios.delete(`http://172.19.100.10:8080/api/markers/${props.marker.id}`, config);
            props.setTrigger(false);
            props.refreshTable(true);
        } catch (error) {
            console.error("Błąd podczas usuwania rekordu", error);
        }
    };
    
    const handleSubmit = async () => {
        try {
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
                date: date
            };
            const config = {
                headers: {
                  'Authorization': `Bearer ${props.token}`,
                },
            };
            await axios.patch(`http://172.19.100.10:8080/api/markers`, data, config);
            props.setTrigger(false);
            props.refreshTable(true);
        } catch (error) {
            console.error("Błąd podczas patchowania", error);
        }
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
            await axios.patch(`http://172.19.100.10:8080/api/markers`, data, config);
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
                        <p><strong>Data dodania: </strong><input type="text" value={date} onChange={handleDateChange}/></p>
                        <p><strong>Typ: </strong>
                            <select value={crayfishType} onChange={handleCrayfishTypeChange}>
                              <option value="SIGNAL">Sygnałowy</option>
                              <option value="AMERICAN">Amerykański</option>
                              <option value="NOBLE">Szlachetny</option>
                              <option value="GALICIAN">Galicyjski</option>
                            </select>
                        </p>
                        <p><strong>Tytuł: </strong><input type="text" value={title} onChange={handleTitleChange}/></p>
                        <p><strong>Opis: </strong><textarea value={description} onChange={handleDescriptionChange}/></p>
                        <p><strong>Status: </strong><button onClick={handleStatusChange}>{verified === true ? "Zweryfikowany" : "Niezweryfikowany" }</button></p>
                        <p><strong>Dodane przez: </strong>{props.marker.userEmail}</p>
                        <button onClick={handleSubmit}>Submit</button>
                    </div>
                </div>
                <button onClick={handleDelete} className="DeleteButton">
                    <span role="img" aria-label="Delete">🗑️</span> Usuń
                </button>
            </div>
        </div>
    ) : "";
}