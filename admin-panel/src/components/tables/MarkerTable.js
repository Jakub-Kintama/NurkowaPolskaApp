import React, {useState} from "react";
import DetailsPopup from "../popups/DetailsPopup";
import { crayfishTypeSwitch, sortMarkers, generateTableHeaders, handleHeaderClick } from "../functions";

export default function MarkerTable({markers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState({id: "", lat: "", lng: "", crayfishType: "", date: "", title: "", description: "", userEmail: ""});
    const [currentHeader, setCurrentHeader] = useState("Status");
    const [sortAs, setSortAs] = useState("asc");

    const headers = ["Data","Koordynaty", "Typ", "Status"];

    const handleDetailsClick = (marker) => {
        setDetailsPopupButton(true);
        setMarkerDetails({
            id: marker._id,
            lat: marker.mapMarker.position.lat,
            lng: marker.mapMarker.position.lng,
            crayfishType: marker.CrayfishType, 
            date: marker.date,
            title: marker.mapMarker.title,
            description: marker.mapMarker.description,
            userEmail: marker.userEmail});
    };

    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };

    return(
    <div className="MarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <thead>
                <tr>
                    {generateTableHeaders(headers, currentHeader, sortAs, handleHeaderClickWrapper)}
                </tr>
            </thead>
            <tbody>
                {sortMarkers(markers, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        <td>{marker.date}</td>
                        <td>{marker.mapMarker.position.lat}, {marker.mapMarker.position.lng}</td>
                        <td>{crayfishTypeSwitch(marker.CrayfishType)}</td>
                        <td>{marker.verified ? "Zweryfikowany" : "Niezweryfikowany" }</td>
                        <td><button className="TableButton" onClick={ () => handleDetailsClick(marker)}>Szczegóły</button></td>
                    </tr>
                ))}
            </tbody>
            
        </table>
        <DetailsPopup trigger={DetailsPopupButton} setTrigger={setDetailsPopupButton} marker={markerDetails}/>          
    </div>
    )
}