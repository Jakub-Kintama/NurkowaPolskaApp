import React from "react";

export default function AdminMarkerTable({markers}) {
    return(
    <div className="AdminMarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <tr>
                <th>Data</th>
                <th>Typ</th>
                <th>Status</th>
            </tr>
            {markers.map( (marker) => (
                <>
                <tr key={marker.id}>
                    <td>{marker.date}</td>
                    <td>{marker.crayfishType}</td>
                    <td>{marker.verified ? "Zweryfikowany" : "Niezweryfikowany" }</td>
                    <td><button className="TableButton">Szczegóły</button></td>
                </tr>
                </>
            ))}
            
        </table>            
    </div>
    )
}