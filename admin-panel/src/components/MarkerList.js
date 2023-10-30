import React from "react";

export default function MarkerList() {
    return(
    <div className="MarkerList">
        <h2>Lista znaczników:</h2>
        <table className="MarkerTable">
            <tr>
                <th>Koordynaty</th>
                <th>Status</th>
                <th>Typ</th>
                <th>Pokaż na mapie</th>
            </tr>
            <tr>
                <td>52°24'04.7"N 16°54'58.6"E</td>
                <td>Zatwierdzony</td>
                <td>Rak Amerykański</td>
                <td><button className="TableButton">Pokaż na mapie</button></td>
            </tr>
            
        </table>            
    </div>
    )
}