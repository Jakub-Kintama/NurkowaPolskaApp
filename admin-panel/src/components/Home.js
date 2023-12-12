import React, {useEffect, useState} from "react";
import MarkerTable from "./tables/MarkerTable";
import AdminView from "./views/AdminView"
import axios from 'axios';
import { saveAs } from 'file-saver';
import LoginForm from "./LoginForm";

export default function Home() {

    const [isLogged, setIsLogged] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    const [markers, setMarkers] = useState([]);
    const [token, setToken] = useState("")
    const [loginPopupButton, setLoginPopupButton] = useState(false);

    const apiMarkers = 'http://localhost:8080/api/markers'

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.get(apiMarkers);
            setMarkers(response.data);
          } catch (error) {
            console.error('Error while fetching data:', error);
          }
        };
        fetchData();
    }, []);

    const exportMarkers = () => {
        const markersJson = JSON.stringify(markers, null, 2);
        const blob = new Blob([markersJson], { type: 'application/json' });
        saveAs(blob, 'markers.json');
    };

    const handleLoginSuccess = (token, refreshToken, role) => {
        setIsLogged(true);
        setToken(token);
        setIsAdmin(role === 'ADMIN');
    };

    const handleLoginError = (error) => {
        console.error("Błąd logowania:", error);
    };
    
    return (
        <>
        <div className="App">
            <div className="Topnav">
                <button className='TopnavButton' id='homeButton'>Powrót</button>
                <button className='TopnavButton' id='exportButton' onClick={exportMarkers}>Eksportuj znaczniki</button>
            </div>
            <div className="PanelPart">
                {isAdmin && isLogged && (
                    <div className="ListContainer">
                        <button onClick={ () => setIsAdmin(false) } className="LogoutButton">Wyloguj</button><br/>
                        <AdminView markers={markers} token={token}/>
                    </div>
                )}
                {!isAdmin && (
                    <>
                    <button onClick={ () => setLoginPopupButton(true) } className="LogoutButton">Zaloguj</button><br/>
                    <MarkerTable markers={markers}/>
                    </>
                )}
            </div>
            <LoginForm 
                trigger={loginPopupButton} 
                setTrigger={setLoginPopupButton} 
                onLoginSuccess={handleLoginSuccess}
                onLoginError={handleLoginError}
            />
        </div>
        </>
    );
    
}