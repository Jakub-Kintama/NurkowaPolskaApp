import React, {useState} from "react";
import AddAdminPopup from "./AddAdminPopup";
import EditAdminPopup from "./EditAdminPopup";

export default function AdminTable({markers}) {

    const [AddAdminPopupButton, setAddAdminPopupButton] = useState(false);
    const [EditAdminPopupButton, setEditAdminPopupButton] = useState(false);
    const [adminDetails, setAdminDetails] = useState([])

    return(
        <div>
            <h2>Lista osób uprawnionych:</h2>
            <table className="AdminTable">
                <thead>
                    <tr>
                        <th>E-Mail</th>
                        <th>Imię</th>
                        <th><button onClick={() => setAddAdminPopupButton(true)} className="TableButton">Dodaj</button></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>*E-Mail osoby uprawnionej*</td>
                        <td>*Imię*</td>
                        <td><button onClick={() => {setEditAdminPopupButton(true); setAdminDetails(["*E-Mail osoby uprawnionej*","*Imię*"])}} className="TableButton">Edytuj</button></td>
                    </tr>
                    <tr>
                        <td>*Inny E-Mail*</td>
                        <td>*Inne Imię*</td>
                        <td><button onClick={() => {setEditAdminPopupButton(true); setAdminDetails(["*Inny E-Mail*","*Inne Imię*"])}} className="TableButton">Edytuj</button></td>
                    </tr>
                </tbody>
            </table>
            <AddAdminPopup trigger={AddAdminPopupButton} setTrigger={setAddAdminPopupButton}></AddAdminPopup>
            <EditAdminPopup trigger={EditAdminPopupButton} setTrigger={setEditAdminPopupButton} email={adminDetails[0]} name={adminDetails[1]}></EditAdminPopup>
        </div>
    )
}