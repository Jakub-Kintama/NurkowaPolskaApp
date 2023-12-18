import React, { useState } from "react";
import { crayfishTypeSwitch } from "../functions";
import axios from "axios";

export default function DetailsPopupForAdmin(props) {

    const [date, setDate] = useState(new Date());
    const [crayfishType, setCrayfishType] = useState("");
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [status, setStatus] = useState("");
    const [userEmail, setUserEmail] = useState("");

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

    const handleUserEmailChange = (e) => {
        setUserEmail(e.target.value);
    }

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
                    title: props.marker.title,
                    description: props.marker.description            
                },
                userEmail: props.marker.userEmail,
                CrayfishType: props.marker.crayfishType,
                date: props.marker.date
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
            props.setTrigger(false);
            props.refreshTable(true);
        } catch (error) {
            console.error("Błąd podczas patchowania", error);
        }
    };

    return (props.trigger) ? (
        <div className="DetailsPopup">
            <div className="DetailsPopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Znacznik</h2>
                <div className="DetailsPopupContent">
                    <iframe className="DetailsIframe" title={title} src={`https://maps.google.com/maps?q=${props.marker.lat},${props.marker.lng}&z=14&output=embed`} allowFullScreen="" loading="lazy" referrerPolicy="no-referrer-when-downgrade"></iframe>
                    <div className="Details">
                        <p><strong>Data dodania: </strong><input type="text" defaultValue={props.marker.date}/></p>
                        <p><strong>Typ: </strong>
                            <select value={props.marker.crayfishType} onChange={handleCrayfishTypeChange}>
                              <option value="SIGNAL">Sygnałowy</option>
                              <option value="AMERICAN">Amerykański</option>
                              <option value="NOBLE">Szlachetny</option>
                              <option value="GALICIAN">Galicyjski</option>
                            </select>
                        </p>
                        <p><strong>Tytuł: </strong><input type="text" defaultValue={props.marker.title}/></p>
                        <p><strong>Opis: </strong><textarea value={props.marker.description} onChange={handleDescriptionChange}/></p>
                        <p><strong>Status: </strong><button onClick={handleStatusChange}>{props.marker.verified === true ? "Zweryfikowany" : "Niezweryfikowany" }</button></p>
                        <p><strong>Dodane przez: </strong><input type="text" defaultValue={props.marker.userEmail}/></p>
                    </div>
                </div>
                <button onClick={handleDelete} className="DeleteButton">
                    <span role="img" aria-label="Delete">🗑️</span> Usuń
                </button>
            </div>
        </div>
    ) : "";
}