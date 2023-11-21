import React, {useState} from "react";
import DetailsPopup from "./DetailsPopup";

export default function MarkerTable({markers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState([])

    return(
    <div className="MarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <thead>
                <tr>
                    <th>Koordynaty</th>
                    <th>Typ</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                {markers.map( (marker) => (
                    <>
                    <tr key={marker.id}>
                        <td>{marker.mapMarker.position.lat}, {marker.mapMarker.position.lng}</td>
                        <td>{marker.CrayfishType}</td>
                        <td>{marker.verified ? "Zweryfikowany" : "Niezweryfikowany" }</td>
                        <td><button className="TableButton" onClick={ () => {setDetailsPopupButton(true); setMarkerDetails([marker._id, marker.mapMarker.position.lat,marker.mapMarker.position.lng, marker.date, marker.CrayfishType, marker.mapMarker.title, marker.mapMarker.description, marker.userEmail])}}>Szczegóły</button></td>
                    </tr>
                    </>
                ))}
            </tbody>
            
        </table>
        <DetailsPopup trigger={DetailsPopupButton} setTrigger={setDetailsPopupButton} marker={markerDetails}/>          
    </div>
    )
}