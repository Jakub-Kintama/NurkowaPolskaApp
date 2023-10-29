import React, {useState} from "react";
import LoginForm from "./LoginForm";
import AddPopup from "./AddPopup";
import EditPopup from "./EditPopup";

export default function Home() {

    const [isAdmin, setIsAdmin] = useState(false);
    const [AddPopupButton, setAddPopupButton] = useState(false);
    const [EditPopupButton, setEditPopupButton] = useState(false);
    const [adminDetails, setAdminDetails] = useState([])
    
    return (
        <>
        <div className="App">
            <div className="Topnav">
                <button className='TopnavButton' id='homeButton'>Powrót</button>
                <button className='TopnavButton' id='exportButton'>Eksportuj znaczniki</button>
            </div>
            <div className="PanelPart">
                {isAdmin && (
                    <div className='AdminList'>
                        <button onClick={ () => setIsAdmin(false) } className="LogoutButton">Wyloguj</button><br/>
                        <p>Lista osób uprawnionych:</p>
                        <table className="AdminTable">
                            <tr>
                                <th>E-Mail</th>
                                <th>Imię</th>
                                <th><button onClick={() => setAddPopupButton(true)} className="TableButton">Dodaj</button></th>
                            </tr>
                            <tr>
                                <td>*E-Mail osoby uprawnionej*</td>
                                <td>*Imię*</td>
                                <td><button onClick={() => {setEditPopupButton(true); setAdminDetails(["*E-Mail osoby uprawnionej*","*Imię*"])}} className="TableButton">Edytuj</button></td>
                            </tr>
                            <tr>
                                <td>*Inny E-Mail*</td>
                                <td>*Inne Imię*</td>
                                <td><button onClick={() => {setEditPopupButton(true); setAdminDetails(["*Inny E-Mail*","*Inne Imię*"])}} className="TableButton">Edytuj</button></td>
                            </tr>
                        </table>
                        <AddPopup trigger={AddPopupButton} setTrigger={setAddPopupButton}></AddPopup>
                        <EditPopup trigger={EditPopupButton} setTrigger={setEditPopupButton} email={adminDetails[0]} name={adminDetails[1]}></EditPopup>
                    </div>
                )}
                {!isAdmin && (
                    <LoginForm setIsAdmin={setIsAdmin}/>
                )}
            </div>
        </div>
        </>
    );
    
}