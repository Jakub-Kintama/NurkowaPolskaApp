import React, {useState} from "react";
import AdminMarkerTable from "./AdminMarkerTable"
import AdminTable from "./AdminTable";

export default function AdminView({markers}) {

    const [markersVisible, setMarkersVisible] = useState(true)
    const [usersVisible, setUsersVisible] = useState(false)

    const markersHandler = () => {
        setMarkersVisible(true);
        setUsersVisible(false);
    }
    const usersHandler = () => {
        setMarkersVisible(false);
        setUsersVisible(true);
    }

    return(
        <div className='AdminList'>
            <button className="markersButton" onClick={() => markersHandler()}>Znaczniki</button>
            <button className="adminsButton" onClick={() => usersHandler()}>Administratorzy</button>
            {markersVisible && (
                <AdminMarkerTable markers={markers}/>
            )}
            {usersVisible && (
                <AdminTable/>
            )}
        </div>
        
    )
}