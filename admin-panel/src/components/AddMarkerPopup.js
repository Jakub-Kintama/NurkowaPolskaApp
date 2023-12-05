import React from "react";

export default function AddMarkerPopup(props) {
    return (props.trigger) ? (
        <div className="Popup">
            <div className="PopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button><br/>
                <h2>Dodaj</h2>
                <input type="text"></input>
                <button>Submit</button>
            </div>
        </div>
    ) : "";
}