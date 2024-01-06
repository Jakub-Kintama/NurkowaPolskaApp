import AddMarkerPopupFN from "../popups/AddMarkerPopupFN";
import React, { useState } from "react";

export default function UserMarkerTable({markers, token, email, refreshTable}){

    const [AddMarkerPopupButton, setAddMarkerPopupButton] = useState(false);

    return(
        <div className="UserMarkerTable">
            {AddMarkerPopupButton && (
                <AddMarkerPopupFN trigger={AddMarkerPopupButton} setTrigger={setAddMarkerPopupButton} token={token} email={email} refreshTable={refreshTable}></AddMarkerPopupFN>
            )}
        </div>
    )

} 