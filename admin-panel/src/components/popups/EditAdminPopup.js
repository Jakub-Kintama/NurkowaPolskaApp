import React from "react";
import axios from "axios";

export default function EditAdminPopup(props) {

    const handleDelete = async () => {
        try {
          const config = {
            headers: {
              'Authorization': `Bearer ${props.token}`,
            },
          };
    
          await axios.delete(`http://172.19.100.10:8080/api/users/${props.email}`, config);
          props.setTrigger(false);
          props.refreshTable(true);
    
        } catch (error) {
          console.error("Błąd podczas przesyłania danych:", error);
        }
    };

    return (props.trigger) ? (
        <div className="Popup">
            <div className="PopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Edytuj</h2>
                <input type="text" value={props.email}></input>
                <button>Submit</button>
                <button onClick={handleDelete} className="DeleteButton">
                    <span role="img" aria-label="Delete">🗑️</span> Usuń
                </button>
            </div>
        </div>
    ) : "";
}