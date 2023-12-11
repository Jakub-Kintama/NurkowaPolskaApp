import React, {useState} from "react";
import DetailsPopup from "../popups/DetailsPopup";
import AddMarkerPopupFN from "../popups/AddMarkerPopupFN";
import { crayfishTypeSwitch, sortMarkers, generateTableHeaders, handleHeaderClick } from "../functions";

export default function AdminMarkerTable({markers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [AddMarkerPopupButton, setAddMarkerPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState([]);
    const [currentHeader, setCurrentHeader] = useState("Status");
    const [sortAs, setSortAs] = useState("asc");

    const headers = ["Data", "Typ", "Status"];

    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };

    return(
    <div className="AdminMarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <thead>
            <tr>
                {generateTableHeaders(headers,currentHeader,sortAs,handleHeaderClickWrapper)}
                <th><button onClick={() => setAddMarkerPopupButton(true)} className="TableButton">Dodaj</button></th>
            </tr>
            </thead>
            <tbody>
                {sortMarkers(markers, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        <td>{marker.date}</td>
                        <td>{crayfishTypeSwitch(marker.CrayfishType)}</td>
                        <td>{marker.verified ? "Zweryfikowany" : "Niezweryfikowany" }</td>
                        <td><button className="TableButton" onClick={ () => {setDetailsPopupButton(true); setMarkerDetails([marker._id, marker.mapMarker.position.lat,marker.mapMarker.position.lng, marker.date, marker.CrayfishType, marker.mapMarker.title, marker.mapMarker.description, marker.userEmail])}}>Szczegóły</button></td>
                    </tr>
                ))}
            </tbody>  
        </table>   
        <DetailsPopup trigger={DetailsPopupButton} setTrigger={setDetailsPopupButton} marker={markerDetails}/>  
        <AddMarkerPopupFN trigger={AddMarkerPopupButton} setTrigger={setAddMarkerPopupButton}></AddMarkerPopupFN>
    </div>
    )
}