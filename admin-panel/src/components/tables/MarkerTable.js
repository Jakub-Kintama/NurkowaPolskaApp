import React, {useState} from "react";
import DetailsPopup from "../popups/DetailsPopup";
import { crayfishTypeSwitch, getSortedFilteredMarkers, generateTableHeaders, handleHeaderClick, handleDetailsClick, handleCheckboxChange } from "../functions";
import YearPicker from "../other/YearPicker";

export default function MarkerTable({markers, downloadTrigger, selectedMarkers, setSelectedMarkers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState({id: "", lat: "", lng: "", crayfishType: "", date: "", title: "", description: "", userEmail: ""});
    
    const headers = ["Data","Tytuł", "Typ", "Status", ""];
    const [sortAs, setSortAs] = useState("asc");
    const [currentHeader, setCurrentHeader] = useState("Status");

    const [selectedYear, setSelectedYear] = useState("");

    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };

    const handleDetailsClickWrapper = (marker) => {
        handleDetailsClick(marker, setDetailsPopupButton, setMarkerDetails);
    };

    return(
    <div className="MarkerList">
        <h2>Lista znaczników:</h2>
        <YearPicker markers={markers} selectedYear={selectedYear} setSelectedYear={setSelectedYear}/>
        <table className="MarkerTable">
            <thead className="NormalTableThead">
                <tr>
                    {downloadTrigger ? <p/> : ""}
                    {generateTableHeaders(headers, currentHeader, sortAs, handleHeaderClickWrapper)}
                </tr>
            </thead>
            <tbody>
                {getSortedFilteredMarkers(markers, selectedYear, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        {downloadTrigger ? 
                            <td>
                                <input
                                    type="checkbox"
                                    value={marker}
                                    checked={selectedMarkers.includes(marker)}
                                    onChange={() => handleCheckboxChange(marker, selectedMarkers, setSelectedMarkers)}
                                />
                            </td> : ""}
                        <td>{marker.date}</td>
                        <td>{marker.mapMarker.title}</td>
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