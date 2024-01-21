import React, {useState} from "react";
import AddMarkerPopupFN from "../popups/AddMarkerPopup";
import { crayfishTypeSwitch, sortMarkers, generateTableHeaders, handleHeaderClick, handleDetailsClick, handleCheckboxChange, getSortedFilteredMarkers } from "../functions";
import DetailsPopupWithEditing from "../popups/DetailsPopupWithEditing";
import YearPicker from "../other/YearPicker";

export default function MarkerTableForLoggedUsers({markers, token, email, role, refreshTable, downloadTrigger, selectedMarkers, setSelectedMarkers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [AddMarkerPopupButton, setAddMarkerPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState({id: "", lat: "", lng: "", crayfishType: "", date: "", title: "", description: "", verified: null, userEmail: ""});
    const [currentHeader, setCurrentHeader] = useState("Status");
    const [sortAs, setSortAs] = useState("asc");
    const [selectedYear, setSelectedYear] = useState("");

    const headers = ["Data", "Tytuł", "Typ", "Status"];

    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };

    const handleDetailsClickWrapper = (marker) => {
        handleDetailsClick(marker, setDetailsPopupButton, setMarkerDetails);
    };

    const personalMarkers = markers.filter(marker => marker.userEmail === email);

    return(
    <div className="AdminMarkerList">
        <h2>Lista znaczników:</h2>
        <YearPicker markers={markers} selectedYear={selectedYear} setSelectedYear={setSelectedYear}/>
        <table className="MarkerTable">
            <thead>
            <tr>
                {downloadTrigger ? <p/> : ""}
                {generateTableHeaders(headers,currentHeader,sortAs,handleHeaderClickWrapper)}
                <th><button onClick={() => setAddMarkerPopupButton(true)} className="TableAddButton">Dodaj</button></th>
            </tr>
            </thead>
            <tbody>
                {role === "ADMIN" ? getSortedFilteredMarkers(markers, selectedYear, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        {downloadTrigger ? 
                            <td>
                                <input
                                    type="checkbox"
                                    value={marker}
                                    checked={selectedMarkers.includes(marker)}
                                    onChange={() => handleCheckboxChange(marker, selectedMarkers, setSelectedMarkers)}
                                />
                            </td> : ""
                        }
                        <td>{marker.date}</td>
                        <td>{marker.mapMarker.title}</td>
                        <td>{crayfishTypeSwitch(marker.CrayfishType)}</td>
                        <td className={marker.verified ? "Verified" : "UnverifiedText"}>
                            {marker.verified ? "Zweryfikowany" : "Niezweryfikowany"}
                        </td>
                        <td><button className="TableButton" onClick={ () => handleDetailsClickWrapper(marker) }>Szczegóły</button></td>
                    </tr>
                )) : getSortedFilteredMarkers(personalMarkers, selectedYear, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        {downloadTrigger ? 
                            <td>
                                <input
                                    type="checkbox"
                                    value={marker}
                                    checked={selectedMarkers.includes(marker)}
                                    onChange={() => handleCheckboxChange(marker, selectedMarkers, setSelectedMarkers)}
                                />
                            </td> : ""
                        }
                        <td>{marker.date}</td>
                        <td>{marker.mapMarker.title}</td>
                        <td>{crayfishTypeSwitch(marker.CrayfishType)}</td>
                        <td className={marker.verified ? "" : "UnverifiedText"}>
                            {marker.verified ? "Zweryfikowany" : "Niezweryfikowany"}
                        </td>
                        <td><button className="TableButton" onClick={ () => handleDetailsClickWrapper(marker) }>Szczegóły</button></td>
                    </tr>
                ))}
            </tbody>  
        </table>
        {DetailsPopupButton && (
            <DetailsPopupWithEditing role={role} trigger={DetailsPopupButton} setTrigger={setDetailsPopupButton} marker={markerDetails} token={token} refreshTable={refreshTable}/>
        )}
        {AddMarkerPopupButton && (
            <AddMarkerPopupFN trigger={AddMarkerPopupButton} setTrigger={setAddMarkerPopupButton} token={token} email={email} role={role} refreshTable={refreshTable}></AddMarkerPopupFN>
        )}
    </div>
    )
}