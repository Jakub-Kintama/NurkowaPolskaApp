import React, {useState} from "react";
import DetailsPopup from "../popups/DetailsPopup";
import { crayfishTypeSwitch, sortMarkers, generateTableHeaders, handleHeaderClick, handleDetailsClick } from "../functions";

export default function MarkerTable({markers, downloadTrigger, setDownloadTrigger, selectedMarkers, setSelectedMarkers, handleDownload}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState({id: "", lat: "", lng: "", crayfishType: "", date: "", title: "", description: "", userEmail: ""});
    
    const headers = ["Data","Koordynaty", "Typ", "Status"];
    const [sortAs, setSortAs] = useState("asc");
    const [currentHeader, setCurrentHeader] = useState("Status");

    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };

    const handleDetailsClickWrapper = (marker) => {
        handleDetailsClick(marker, setDetailsPopupButton, setMarkerDetails);
    };

    const handleCheckboxChange = (marker) => {
        // Check if the marker is already in the selectedMarkers list
        const isSelected = selectedMarkers.some((selectedMarker) => selectedMarker === marker);
    
        if (isSelected) {
          // If it's selected, remove it from the list
          setSelectedMarkers((prevSelectedMarkers) =>
            prevSelectedMarkers.filter((selectedMarker) => selectedMarker !== marker)
          );
        } else {
          // If it's not selected, add it to the list
          setSelectedMarkers((prevSelectedMarkers) => [...prevSelectedMarkers, marker]);
        }
    };

    return(
    <div className="MarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <thead>
                <tr>
                    {downloadTrigger ? <th><button onClick={handleDownload}>Pobierz</button></th> : ""}
                    {generateTableHeaders(headers, currentHeader, sortAs, handleHeaderClickWrapper)}
                </tr>
            </thead>
            <tbody>
                {sortMarkers(markers, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        {downloadTrigger ? 
                            <td>
                                <input
                                    type="checkbox"
                                    value={marker}
                                    checked={selectedMarkers.includes(marker)}
                                    onChange={() => handleCheckboxChange(marker)}
                                />
                            </td> : ""}
                        <td>{marker.date}</td>
                        <td>{marker.mapMarker.position.lat}, {marker.mapMarker.position.lng}</td>
                        <td>{crayfishTypeSwitch(marker.CrayfishType)}</td>
                        <td className={marker.verified ? "" : "UnverifiedText"}>
                            {marker.verified ? "Zweryfikowany" : "Niezweryfikowany"}
                        </td>
                        <td><button className="TableButton" onClick={ () => handleDetailsClickWrapper(marker)}>Szczegóły</button></td>
                    </tr>
                ))}
            </tbody>
            
        </table>
        {DetailsPopupButton && (
            <DetailsPopup trigger={DetailsPopupButton} setTrigger={setDetailsPopupButton} marker={markerDetails}/>          
        )}
    </div>
    )
}