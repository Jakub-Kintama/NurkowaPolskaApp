import React, {useEffect, useState} from "react";
import MarkerTable from "./MarkerTable";
import AdminView from "./AdminView";
import axios from 'axios';
import { saveAs } from 'file-saver';

export default function Home() {

    const [isAdmin, setIsAdmin] = useState(false);
    const [markers, setMarkers] = useState([]);

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
    
    return (
        <>
        <div className="App">
            <div className="Topnav">
                <button className='TopnavButton' id='homeButton'>Powrót</button>
                <button className='TopnavButton' id='exportButton' onClick={exportMarkers}>Eksportuj znaczniki</button>
            </div>
            <div className="PanelPart">
                {isAdmin && (
                    <div className="ListContainer">
                        <button onClick={ () => setIsAdmin(false) } className="LogoutButton">Wyloguj</button><br/>
                        <AdminView markers={markers}/>
                    </div>
                )}
                {!isAdmin && (
                    <>
                    <button onClick={ () => setIsAdmin(true) } className="LogoutButton">Zaloguj</button><br/>
                    <MarkerTable markers={markers}/>
                    </>
                )}
            </div>
        </div>
        </>
    );
    
}