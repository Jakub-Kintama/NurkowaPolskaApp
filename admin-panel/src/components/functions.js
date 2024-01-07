export const baseURL = "http://172.19.100.10:8080";

export function crayfishTypeSwitch(param) {
    switch(param) {
        case "GALICIAN":
            return "Galicyjski";
        case "SIGNAL":
            return "Sygnałowy";
        case "AMERICAN":
            return "Amerykański";
        case "NOBLE":
            return "Szlachetny";
        case "OTHER":
            return "Inne";
        default:
            return "WHAT IS THAT MELODY";
    }
}

export const generateTableHeaders = (headers, currentHeader, sortAs, handleHeaderClick) => {
    return headers.map((header, index) => (
        <th key={index} onClick={() => handleHeaderClick(header)}>
            {header}
            {currentHeader === header && (
                <span>
                    {sortAs === "asc" ? " ▲" : " ▼"}
                </span>
            )}
        </th>
    ));
};

export const handleHeaderClick = (header, currentHeader, sortAs, setCurrentHeader, setSortAs) => {
    if (header === currentHeader) {
        sortAs === "asc" ? setSortAs("desc") : setSortAs("asc");
    } else {
        setCurrentHeader(header);
    }
};

export const handleDetailsClick = (marker, setDetailsPopupButton, setMarkerDetails) => {
    setDetailsPopupButton(true);
    setMarkerDetails({
        id: marker._id,
        lat: marker.mapMarker.position.lat,
        lng: marker.mapMarker.position.lng,
        crayfishType: marker.CrayfishType, 
        date: marker.date,
        title: marker.mapMarker.title,
        description: marker.mapMarker.description,
        verified: marker.verified,
        userEmail: marker.userEmail});
};

export const sortMarkers = (data, header, sortAs) => {
    data.sort( (a,b) => b.date.localeCompare(a.date))
    switch(header){
        case "Status":
            if(sortAs === "asc"){
                return data.sort( (a,b) => a.verified - b.verified);
            }
            return data.sort( (a,b) => b.verified - a.verified);

        case "Typ":
            if(sortAs === "asc"){
                return data.sort( (a,b) => a.CrayfishType.localeCompare(b.CrayfishType));
            }
            return data.sort( (a,b) => b.CrayfishType.localeCompare(a.CrayfishType));

        case "Data":
            if(sortAs === "asc"){
                return data.sort( (a,b) => a.date.localeCompare(b.date));
            }
            return data.sort( (a,b) => b.date.localeCompare(a.date));

        case "Koordynaty":
            if(sortAs === "asc"){
                return data.sort( (a,b) => a.mapMarker.position.lat - b.mapMarker.position.lat);
            }
            return data.sort( (a,b) => b.mapMarker.position.lat - a.mapMarker.position.lat);

        default:
            return data.sort( (a,b) => a.verified - b.verified);
    }
    
};
export const sortUsers = (data, header, sortAs) => {
    data.sort( (a,b) => a.email.localeCompare(b.email));
    switch(header){
        case "E-Mail":
            if(sortAs === "asc"){
                return data.sort( (a,b) => a.email.localeCompare(b.email));
            }
            return data.sort( (a,b) => b.email.localeCompare(a.email));

        case "Rola":
            if(sortAs === "asc"){
                return data.sort( (a,b) => a.role.localeCompare(b.role));
            }
            return data.sort( (a,b) => b.role.localeCompare(a.role));

        default:
            return data.sort( (a,b) => a.role.localeCompare(b.role));
    }
    
};