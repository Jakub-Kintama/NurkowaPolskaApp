import React from "react";

export default function AdminMarkerList(markers) {
    return(
    <div className="AdminMarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <tr>
                <th>Data</th>
                <th>Koordynaty</th>
                <th>Typ</th>
                <th>Status</th>
            </tr>
            <tr>
                <td>20.12.2023</td>
                <td>52°24'04.7"N 16°54'58.6"E</td>
                <td>Rak Amerykański</td>
                <td>Zatwierdzony</td>
                <td><button className="TableButton">Szczegóły</button></td>
            </tr>
            
        </table>            
    </div>
    )
}