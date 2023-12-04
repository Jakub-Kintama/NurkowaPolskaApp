import React, {useState} from "react";
import DetailsPopup from "./DetailsPopup";
import { crayfishTypeSwitch } from "./functions";

export default function MarkerTable({markers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState([])

    const headers = ["Koordynaty", "Typ", "Status"]


    return(
    <div className="MarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <thead>
                <tr>
                    {headers.map( (header, index) => (
                        <th key={index}>{header}</th>
                    ))
                    }
                </tr>
            </thead>
            <tbody>
                {markers.map( (marker, index) => (
                    <tr key={index}>
                        <td key={index}>{marker.mapMarker.position.lat}, {marker.mapMarker.position.lng}</td>
                        <td key={index + 1}>{crayfishTypeSwitch(marker.CrayfishType)}</td>
                        <td key={index + 2}>{marker.verified ? "Zweryfikowany" : "Niezweryfikowany" }</td>
                        <td key={index + 3}><button className="TableButton" onClick={ () => {setDetailsPopupButton(true); setMarkerDetails([marker._id, marker.mapMarker.position.lat,marker.mapMarker.position.lng, marker.date, marker.CrayfishType, marker.mapMarker.title, marker.mapMarker.description, marker.userEmail])}}>Szczegóły</button></td>
                    </tr>
                ))}
            </tbody>
            
        </table>
        <DetailsPopup trigger={DetailsPopupButton} setTrigger={setDetailsPopupButton} marker={markerDetails}/>          
    </div>
    )
}