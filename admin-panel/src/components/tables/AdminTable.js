import React, {useState, useEffect} from "react";
import AddAdminPopup from "../popups/AddAdminPopup";
import EditAdminPopup from "../popups/EditAdminPopup";
import axios from 'axios';
import { sortUsers, generateTableHeaders, handleHeaderClick } from "../functions";

export default function AdminTable() {

    const [admins, setAdmins] = useState([]);
    const apiAdmins = 'http://localhost:8080/api/users'

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.get(apiAdmins);
            setAdmins(response.data);
          } catch (error) {
            console.error('Error while fetching data:', error);
          }
        };
        fetchData();
    }, [admins]);

    const [AddAdminPopupButton, setAddAdminPopupButton] = useState(false);
    const [EditAdminPopupButton, setEditAdminPopupButton] = useState(false);
    const [selectedAdmin, setSelectedAdmin] = useState();

    const headers = ["E-Mail", "Rola"];
    const [currentHeader, setCurrentHeader] = useState("Rola");
    const [sortAs, setSortAs] = useState("asc");

    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };
    
    return(
        <div>
            <h2>Lista osób uprawnionych:</h2>
            <table className="AdminTable">
                <thead>
                    <tr>
                        {generateTableHeaders(headers, currentHeader, sortAs, handleHeaderClickWrapper)}
                        <th><button onClick={() => setAddAdminPopupButton(true)} className="TableButton">Dodaj</button></th>
                    </tr>
                </thead>
                <tbody>
                    {sortUsers(admins, currentHeader, sortAs).map( (admin, index) => (
                        <tr key={index} className="adminRow">
                            <td>{admin.email}</td>
                            <td>{admin.role}</td>
                            <td><button onClick={() => {setEditAdminPopupButton(true); setSelectedAdmin(admin.id)}} className="TableButton">Edytuj</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <AddAdminPopup trigger={AddAdminPopupButton} setTrigger={setAddAdminPopupButton}></AddAdminPopup>
            <EditAdminPopup trigger={EditAdminPopupButton} setTrigger={setEditAdminPopupButton} email={selectedAdmin}></EditAdminPopup>
        </div>
    )
}