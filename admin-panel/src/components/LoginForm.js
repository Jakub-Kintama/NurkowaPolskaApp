import React, { useState } from 'react';
import axios from 'axios';

export default function LoginForm( props ) {
    
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")


    const handleEmailChange = (e) => {
        setEmail(e.target.value);
      };
    
    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleSubmit = async () => {
        try {
            const data = {
                email: email,
                password: password
            }
            const response = await axios.post(`http://localhost:8080/api/auth`, data);
            if (response.data.role[0].authority === "ROLE_ADMIN") {
                props.onLoginSuccess(response.data.accessToken, response.data.refreshToken, "ADMIN");
            } else {
                props.onLoginSuccess(response.data.accessToken, response.data.refreshToken, "USER");
            }
            props.setTrigger(false);
            
        } catch (error) {
            console.error("Błąd podczas autoryzacji", error);
            props.onLoginError(error);
        }
    };

    return (props.trigger) ? (
        <div className='Popup'>
            <div className='PopupInner'>
                <button onClick={ () => props.setTrigger(false) } className="CloseButton">Zamknij</button>
                <h2>Proszę się zalogować</h2>
                <input type='text' onChange={handleEmailChange} placeholder='E-Mail'></input>
                <input type='password' onChange={handlePasswordChange} placeholder='Hasło'></input>
                <button onClick={handleSubmit} disabled={!email || !password}>Submit</button>
            </div>
        </div>
    ) : "";
}