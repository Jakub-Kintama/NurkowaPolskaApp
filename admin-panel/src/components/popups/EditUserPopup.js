import React, { useState } from "react";
import axios from "axios";

export default function EditUserPopup(props) {
  const [email, setEmail] = useState(props.user.email);
  const [role, setRole] = useState(props.user.role);

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleRoleChange = (e) => {
    setRole(e.target.id);
  };

  const handleSubmit = async () => {
    try {
        const data = {
            email: email,
            role: role
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

  const handleDelete = async () => {
    
    try {
      const config = {
        headers: {
          'Authorization': `Bearer ${props.token}`,
        },
      };

      await axios.delete(`http://172.19.100.10:8080/api/users/${email}`, config);
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
              <p>
                <strong>E-Mail: </strong>
                <input type="text" onChange={handleInputChange} value={email}></input>
              </p>
              <p>
                <strong>Rola: </strong>
                <label htmlFor="ADMIN">Administrator</label>
                <input
                  type="radio"
                  id="ADMIN"
                  name="role"
                  checked={role === "ADMIN"}
                  onChange={handleRoleChange}
                ></input>
                <label htmlFor="USER">Użytkownik</label>
                <input
                  type="radio"
                  id="USER"
                  name="role"
                  checked={role === "USER"}
                  onChange={handleRoleChange}
                ></input>
              </p>
              
              <button onClick={handleSubmit}>Submit</button>
              <br/>
              <button onClick={handleDelete} className="DeleteButton">
                  <span role="img" aria-label="Delete">🗑️</span> Usuń
              </button>
          </div>
      </div>
  ) : "";
}