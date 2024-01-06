import React, {useState} from "react";
import AdminMarkerTable from "../tables/AdminMarkerTable"
import UserTable from "../tables/UserTable";

export default function AdminView({markers, token, email, refreshTable}) {

    const [markersVisible, setMarkersVisible] = useState(true);
    const [usersVisible, setUsersVisible] = useState(false);

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
            <button className="MarkersButton" onClick={() => markersHandler()}>Znaczniki</button>
            <button className="UsersButton" onClick={() => usersHandler()}>Użytkownicy</button>
            {markersVisible && (
                <AdminMarkerTable markers={markers} token={token} email={email} refreshTable={refreshTable}/>
            )}
            {usersVisible && (
                <UserTable token={token}/>
            )}
        </div>
        
    )
}