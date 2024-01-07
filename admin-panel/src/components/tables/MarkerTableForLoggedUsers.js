import React, {useState} from "react";
import AddMarkerPopupFN from "../popups/AddMarkerPopupFN";
import { crayfishTypeSwitch, sortMarkers, generateTableHeaders, handleHeaderClick, handleDetailsClick } from "../functions";
import DetailsPopupWithEditing from "../popups/DetailsPopupWithEditing";

export default function MarkerTableForLoggedUsers({markers, token, email, role, refreshTable}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [AddMarkerPopupButton, setAddMarkerPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState({id: "", lat: "", lng: "", crayfishType: "", date: "", title: "", description: "", verified: null, userEmail: ""});
    const [currentHeader, setCurrentHeader] = useState("Status");
    const [sortAs, setSortAs] = useState("asc");

    const headers = ["Data", "Typ", "Status"];

    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };

    const handleDetailsClickWrapper = (marker) => {
        handleDetailsClick(marker, setDetailsPopupButton, setMarkerDetails);
    };

    const filteredMarkers = markers.filter(marker => marker.userEmail === email);

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
                {role === "ADMIN" ? sortMarkers(markers, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        <td>{marker.date}</td>
                        <td>{crayfishTypeSwitch(marker.CrayfishType)}</td>
                        <td className={marker.verified ? "" : "UnverifiedText"}>
                            {marker.verified ? "Zweryfikowany" : "Niezweryfikowany"}
                        </td>
                        <td><button className="TableButton" onClick={ () => handleDetailsClickWrapper(marker) }>Szczegóły</button></td>
                    </tr>
                )) : sortMarkers(filteredMarkers, currentHeader, sortAs).map( (marker, index) => (
                    <tr key={index}>
                        <td>{marker.date}</td>
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