import React, {useState} from "react";
import LoginForm from "./LoginForm";
import MarkerList from "./MarkerList";
import AdminMarkerList from "./AdminMarkerList";
import AdminList from "./AdminList";

export default function Home() {

    const [isAdmin, setIsAdmin] = useState(false);
    
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
                        <AdminMarkerList/>
                        <AdminList/>
                    </div>
                )}
                {!isAdmin && (
                    <>
                    <MarkerList/>
                    <LoginForm setIsAdmin={setIsAdmin}/>
                    </>
                )}
            </div>
        </div>
        </>
    );
    
}