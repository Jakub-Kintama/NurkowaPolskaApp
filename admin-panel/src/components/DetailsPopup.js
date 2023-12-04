import React from "react";
import { crayfishTypeSwitch } from "./functions";

export default function DetailsPopup(props) {
    return (props.trigger) ? (
        <div className="Popup">
            <div className="PopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Znacznik</h2>
                <iframe title={props} src={`https://maps.google.com/maps?q=${props.marker[1]},${props.marker[2]}&z=14&output=embed`} style={{width: 600, height: 450,border: 0}} allowFullScreen="" loading="lazy" referrerPolicy="no-referrer-when-downgrade"></iframe>
                <p><strong>Data dodania: </strong>{props.marker[3]}</p>
                <p><strong>Typ: </strong>{crayfishTypeSwitch(props.marker[4])}</p>
                <p><strong>Tytuł: </strong>{props.marker[5]}</p>
                <p><strong>Opis: </strong>{props.marker[6]}</p>
                <p><strong>Dodane przez: </strong>{props.marker[7]}</p>
            </div>
        </div>
    ) : "";
}