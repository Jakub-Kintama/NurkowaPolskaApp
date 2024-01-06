import React, {useEffect, useState, useCallback} from "react";
import MarkerTable from "./tables/MarkerTable";
import AdminView from "./views/AdminView"
import axios from 'axios';
import { saveAs } from 'file-saver';
import LoginForm from "./LoginForm";

export default function Home() {

    const [isLogged, setIsLogged] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    const [markers, setMarkers] = useState([]);
    const [token, setToken] = useState("");
    const [email, setEmail] = useState("");
    const [loginPopupButton, setLoginPopupButton] = useState(false);
    const [refreshTable, setRefreshTable] = useState(false);

    const apiMarkers = 'http://172.19.100.10:8080/api/markers'

    const fetchData = useCallback(async () => {
        try {
            const response = await axios.get(apiMarkers);
            setMarkers(response.data);
        } catch (error) {
            console.error('Error while fetching data:', error);
        }
    }, []);

    useEffect(() => {
        fetchData();
        setRefreshTable(false);
    }, [refreshTable, fetchData]);

    const exportMarkers = () => {
        const markersJson = JSON.stringify(markers, null, 2);
        const blob = new Blob([markersJson], { type: 'application/json' });
        saveAs(blob, 'markers.json');
    };

    const handleLoginSuccess = (token, refreshToken, email, role) => {
        setIsLogged(true);
        setToken(token);
        setEmail(email);
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
                        <AdminView markers={markers} token={token} email={email} refreshTable={setRefreshTable}/>
                    </div>
                )}
                {isLogged && !isAdmin && (
                    <>
                    <button onClick={ () => setIsAdmin(false) } className="LogoutButton">Wyloguj</button><br/>
                    <MarkerTable markers={markers}/>
                    </>
                )}
                {!isAdmin && (
                    <>
                    <button onClick={ () => setLoginPopupButton(true) } className="LogoutButton">Zaloguj</button><br/>
                    <MarkerTable markers={markers}/>
                    </>
                )}
            </div>
            {loginPopupButton && (
                <LoginForm 
                    trigger={loginPopupButton} 
                    setTrigger={setLoginPopupButton} 
                    onLoginSuccess={handleLoginSuccess}
                    onLoginError={handleLoginError}
                />
            )}
            
        </div>
        </>
    );
    
}