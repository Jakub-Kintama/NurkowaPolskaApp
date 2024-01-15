import React, { useState } from "react";
import axios from "axios";
import { baseURL } from "../functions";

export default function AddUserPopup(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [selectedRole, setSelectedRole] = useState("");

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleRoleChange = (e) => {
    setSelectedRole(e.target.id);
  };

  const handleSubmit = async () => {
    try {
      const data = {
        email: email,
        password: password ? password : null,
        role: selectedRole
      };

      const config = {
        headers: {
          'Authorization': `Bearer ${props.token}`,
        },
      };

      await axios.post(`${baseURL}/api/users`, data, config);
      setEmail("");
      setSelectedRole("");
      props.setTrigger(false);

    } catch (error) {
      console.error("Błąd podczas przesyłania danych:", error);
    }
  };

  return props.trigger ? (
    <div className="Popup">
      <div className="PopupInner">
        <button onClick={() => props.setTrigger(false)} className="CloseButton">
          Zamknij
        </button>
        <br />
        <h2>Dodaj</h2>
        <input
          type="text"
          value={email}
          onChange={handleInputChange}
        ></input>
        <label htmlFor="ADMIN">Administrator</label>
        <input
          type="password"
          value={password}
          onChange={handlePasswordChange}
        ></input>
        <label htmlFor="ADMIN">Administrator</label>
        <input
          type="radio"
          id="ADMIN"
          name="role"
          checked={selectedRole === "ADMIN"}
          onChange={handleRoleChange}
        ></input>
        <label htmlFor="USER">Użytkownik</label>
        <input
          type="radio"
          id="USER"
          name="role"
          checked={selectedRole === "USER"}
          onChange={handleRoleChange}
        ></input>
        <br />
        <button onClick={handleSubmit}>Submit</button>
      </div>
    </div>
  ) : "";
}