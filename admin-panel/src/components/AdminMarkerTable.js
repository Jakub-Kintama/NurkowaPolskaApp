import React, {useState} from "react";
import DetailsPopup from "./DetailsPopup";

export default function AdminMarkerTable({markers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState([])

    return(
    <div className="AdminMarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <thead>
            <tr>
                <th>Data</th>
                <th>Typ</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            {markers.map( (marker, key) => (
                <>
                <tr key={key}>
                    <td key={key+1}>{marker.date}</td>
                    <td key={key+2}>{marker.CrayfishType}</td>
                    <td key={key+3}>{marker.verified ? "Zweryfikowany" : "Niezweryfikowany" }</td>
                    <td key={key+4}><button className="TableButton" onClick={ () => {setDetailsPopupButton(true); setMarkerDetails([marker._id, marker.mapMarker.position.lat,marker.mapMarker.position.lng, marker.date, marker.CrayfishType, marker.mapMarker.title, marker.mapMarker.description, marker.userEmail])}}>Szczegóły</button></td>
                </tr>
                <DetailsPopup trigger={DetailsPopupButton} setTrigger={setDetailsPopupButton} marker={markerDetails}/>
                </>
            ))}
            </tbody>
            
        </table>            
    </div>
    )
}