import React, {useState, useEffect} from "react";
import AddAdminPopup from "./AddAdminPopup";
import EditAdminPopup from "./EditAdminPopup";
import axios from 'axios';

export default function AdminTable() {

    const [AddAdminPopupButton, setAddAdminPopupButton] = useState(false);
    const [EditAdminPopupButton, setEditAdminPopupButton] = useState(false);
    const [selectedAdmin, setSelectedAdmin] = useState()

    const [admins, setAdmins] = useState([]);
    const apiAdmins = 'http://localhost:8080/api/admins'

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
    }, []);

    const headers = ["E-Mail"];

    return(
        <div>
            <h2>Lista osób uprawnionych:</h2>
            <table className="AdminTable">
                <thead>
                    <tr>
                    {headers.map( (header, index) => (
                        <th key={index}>{header}</th>
                    ))}
                    <th><button onClick={() => setAddAdminPopupButton(true)} className="TableButton">Dodaj</button></th>
                    </tr>
                </thead>
                <tbody>
                    {admins.map( (admin, index) => (
                        <tr key={index} className="adminRow">
                            <td>{admin.id}</td>
                            <td><button onClick={() => {setEditAdminPopupButton(true); setSelectedAdmin(admin.id)}} className="TableButton">Edytuj</button></td>
                        </tr>
                    ))
                    }
                </tbody>
            </table>
            <AddAdminPopup trigger={AddAdminPopupButton} setTrigger={setAddAdminPopupButton}></AddAdminPopup>
            <EditAdminPopup trigger={EditAdminPopupButton} setTrigger={setEditAdminPopupButton} email={selectedAdmin}></EditAdminPopup>
        </div>
    )
}