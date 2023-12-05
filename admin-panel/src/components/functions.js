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
        default:
            return "WHAT IS THAT MELODY";
    }
}

export const sortData = (data, header, sortAs) => {
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