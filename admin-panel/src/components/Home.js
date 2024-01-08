import React, {useEffect, useState, useCallback} from "react";
import MarkerTable from "./tables/MarkerTable";
import AdminView from "./views/AdminView"
import axios from 'axios';
import { saveAs } from 'file-saver';
import LoginForm from "./LoginForm";
import LoggedUserView from "./views/LoggedUserView";
import { baseURL } from "./functions";

export default function Home() {

    const [isLogged, setIsLogged] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    const [markers, setMarkers] = useState([]);
    const [token, setToken] = useState("");
    const [email, setEmail] = useState("");
    const [loginPopupButton, setLoginPopupButton] = useState(false);
    const [refreshTable, setRefreshTable] = useState(false);
    const [downloadTrigger, setDownloadTrigger] = useState(false);
    const [selectedMarkers, setSelectedMarkers] = useState([]);

    const fetchData = useCallback(async () => {
        try {
            const response = await axios.get(`${baseURL}/api/markers`);
            setMarkers(response.data);
        } catch (error) {
            console.error('Error while fetching data:', error);
        }
    }, []);

    useEffect(() => {
        fetchData();
        setRefreshTable(false);
    }, [refreshTable, fetchData]);

    const setTriggerAndClearArr = (trigger) => {
        setDownloadTrigger(trigger);
        setSelectedMarkers([]);
    }
    
    const startExportingProcess = () => {
        setDownloadTrigger(true);
    }
    
    const cancelDownloading = () => {
        setTriggerAndClearArr(false);
    }

    const handleDownload = () => {
        if (selectedMarkers.length > 0) {
            const markersJson = JSON.stringify(selectedMarkers, null, 2);
            const blob = new Blob([markersJson], { type: 'application/json' });
            saveAs(blob, 'selectedMarkers.json');
            setTriggerAndClearArr(false);
        } else {
            alert("Najpierw proszę coś zaznaczyć!");
        }
    }

    const exportAllMarkers = () => {
        const markersJson = JSON.stringify(markers, null, 2);
        const blob = new Blob([markersJson], { type: 'application/json' });
        saveAs(blob, 'markers.json');
        setTriggerAndClearArr(false);
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
                {downloadTrigger 
                    ? <button className='TopnavButton' id='cancelButton' onClick={cancelDownloading}>Anuluj</button>
                    : <button className='TopnavButton' id='exportButton' onClick={startExportingProcess}>Eksportuj znaczniki</button>}
                {downloadTrigger ? <button className='TopnavButton' id='exportButton' onClick={exportAllMarkers}>Eksportuj wszystkie</button> : ""}
            </div>
            <div className="PanelPart">
                {isAdmin && isLogged && (
                    <div className="ListContainer">
                        <button onClick={ () => {setIsAdmin(false); setIsLogged(false)} } className="LogoutButton">Wyloguj</button><br/>
                        <AdminView markers={markers} token={token} email={email} refreshTable={setRefreshTable}/>
                    </div>
                )}
                {isLogged && !isAdmin && (
                    <>
                    <button onClick={ () => setIsLogged(false) } className="LogoutButton">Wyloguj</button><br/>
                    <LoggedUserView markers={markers} token={token} email={email} refreshTable={setRefreshTable}/>
                    </>
                )}
                {!isLogged && !isAdmin && (
                    <>
                    <button onClick={ () => setLoginPopupButton(true) } className="LogoutButton">Zaloguj</button><br/>
                    <MarkerTable markers={markers} downloadTrigger={downloadTrigger} setDownloadTrigger={setDownloadTrigger} selectedMarkers={selectedMarkers} setSelectedMarkers={setSelectedMarkers} handleDownload={handleDownload}/>
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