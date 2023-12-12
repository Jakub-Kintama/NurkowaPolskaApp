import React from "react";
import { crayfishTypeSwitch } from "../functions";
import axios from "axios";

export default function DetailsPopupForAdmin(props) {

    const handleDelete = async () => {
        try {
            await axios.delete(`http://localhost:8080/api/markers/${props.marker.id}`);
            props.setTrigger(false);
        } catch (error) {
            console.error("Błąd podczas usuwania rekordu", error);
        }
    };

    return (props.trigger) ? (
        <div className="DetailsPopup">
            <div className="DetailsPopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Znacznik</h2>
                <div className="DetailsPopupContent">
                    <iframe className="DetailsIframe" title={props.marker.crayfishType} src={`https://maps.google.com/maps?q=${props.marker.lat},${props.marker.lng}&z=14&output=embed`} allowFullScreen="" loading="lazy" referrerPolicy="no-referrer-when-downgrade"></iframe>
                    <div className="Details">
                        <p><strong>Data dodania: </strong>{props.marker.date}</p>
                        <p><strong>Typ: </strong>{crayfishTypeSwitch(props.marker.crayfishType)}</p>
                        <p><strong>Tytuł: </strong>{props.marker.title}</p>
                        <p><strong>Opis: </strong>{props.marker.description}</p>
                        <p><strong>Dodane przez: </strong>{props.marker.userEmail}</p>
                    </div>
                </div>
                <button onClick={handleDelete} className="DeleteButton">
                    <span role="img" aria-label="Delete">🗑️</span> Usuń
                </button>
            </div>
        </div>
    ) : "";
}