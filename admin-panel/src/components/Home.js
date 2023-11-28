import React, {useEffect, useState} from "react";
import LoginForm from "./LoginForm";
import MarkerTable from "./MarkerTable";
import AdminView from "./AdminView";
import axios from 'axios';

export default function Home() {

    const [isAdmin, setIsAdmin] = useState(false);
    const [markers, setMarkers] = useState([]);

    const apiUrl = 'http://localhost:8080/api/markers'

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.get(apiUrl);
            setMarkers(response.data);
          } catch (error) {
            console.error('Error while fetching data:', error);
          }
        };
        fetchData();
      }, []);
    
    return (
        <>
        <div className="App">
            <div className="Topnav">
                <button className='TopnavButton' id='homeButton'>Powrót</button>
                <button className='TopnavButton' id='exportButton'>Eksportuj znaczniki</button>
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
                    <MarkerTable markers={markers}/>
                    <LoginForm setIsAdmin={setIsAdmin}/>
                    </>
                )}
            </div>
        </div>
        </>
    );
    
}