import React from "react";

export default function EditMarkerPopup(props) {
    return (props.trigger) ? (
        <div className="Popup">
            <div className="PopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Edytuj</h2>
                <input type="text" value={props.lat}></input>
                <input type="text" value={props.lng}></input>
                <input type="text" value={props.date}></input>
                <input type="text" value={props.type}></input>
                <button>Submit</button>
            </div>
        </div>
    ) : "";
}