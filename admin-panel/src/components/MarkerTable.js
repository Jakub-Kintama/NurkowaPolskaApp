import React, {useState} from "react";
import DetailsPopup from "./DetailsPopup";
import { crayfishTypeSwitch, sortData } from "./functions";

export default function MarkerTable({markers}) {

    const [DetailsPopupButton, setDetailsPopupButton] = useState(false);
    const [markerDetails, setMarkerDetails] = useState([]);
    const [currentHeader, setCurrentHeader] = useState("Status");
    const [sortAs, setSortAs] = useState("asc");

    const headers = ["Data","Koordynaty", "Typ", "Status"];

    const handleDetailsClick = (marker) => {
        setDetailsPopupButton(true);
        setMarkerDetails([
            marker._id,
            marker.mapMarker.position.lat,
            marker.mapMarker.position.lng,
            marker.date, marker.CrayfishType,
            marker.mapMarker.title,
            marker.mapMarker.description,
            marker.userEmail]);
    };

    const handleHeaderClick = (header, sort) => {
        header === currentHeader ? (sort === "asc" ? setSortAs("desc") : setSortAs("asc")) : setCurrentHeader(header)
    };

    return(
    <div className="MarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <thead>
                <tr>
                    {headers.map( (header, index) => (
                        <th key={index} onClick={() => handleHeaderClick(header, sortAs)}>
                            {header}
                            {currentHeader === header && (
                                <span>
                                    {sortAs === "asc" ? " ▲" : " ▼"}
                                </span>
                            )}
                        </th>
                    ))
                    }
                </tr>
            </thead>
            <tbody>
                {sortData(markers, currentHeader, sortAs).map( (marker, index) => (
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