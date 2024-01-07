import React, { useState } from "react";
import { baseURL } from "../functions";

const [email, setEmail] = useState("");
const [password, setPassword] = useState("");
const [token, setToken] = useState("");
const [role, setRole] = useState("");

const handleSubmit = async () => {
    try {
      const data = {
        email: email,
        password: password
      };

      const response = await axios.post(`${baseURL}/api/auth`, data);
      props.setTrigger(false);

    } catch (error) {
      console.error("Błąd podczas przesyłania danych:", error);
    }
};

export default function LoginPopup(props) {
    return (props.trigger) ? (
        <div className="Popup">
            <div className="PopupInner">
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Zaloguj się</h2>
                <input type="text" value={email}></input>
                <input type="password" value={password}></input>
                <button onClick={() => handleSubmit}>Submit</button>
            </div>
        </div>
    ) : "";
}
