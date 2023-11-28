import React from "react";

export default function EditAdminPopup(props) {
    return (props.trigger) ? (
        <div className="Popup">
            <div className="PopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Edytuj</h2>
                <input type="text" value={props.email}></input>
                <input type="text" value={props.name}></input>
                <button>Submit</button>
            </div>
        </div>
    ) : "";
}