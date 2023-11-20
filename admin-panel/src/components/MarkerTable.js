import React from "react";

export default function MarkerTable({markers}) {
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
                        <td>{marker.crayfishType}</td>
                        <td>{marker.verified ? "Zweryfikowany" : "Niezweryfikowany" }</td>
                        <td><button className="TableButton">Szczegóły</button></td>
                    </tr>
                    </>
                ))}
            </tbody>
            
        </table>            
    </div>
    )
}