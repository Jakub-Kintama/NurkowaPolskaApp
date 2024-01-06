import React, {useState, useEffect, useCallback} from "react";
import AddUserPopup from "../popups/AddUserPopup";
import EditUserPopup from "../popups/EditUserPopup";
import axios from 'axios';
import { sortUsers, generateTableHeaders, handleHeaderClick } from "../functions";

export default function UserTable( {token} ) {

    const [users, setUsers] = useState([]);
    const [refreshTable, setRefreshTable] = useState(false);
    const [AddUserPopupButton, setAddUserPopupButton] = useState(false);
    const [EditUserPopupButton, setEditUserPopupButton] = useState(false);
    const [selectedUser, setSelectedUser] = useState({email: "", role: ""});

    const headers = ["E-Mail", "Rola"];
    const [currentHeader, setCurrentHeader] = useState("Rola");
    const [sortAs, setSortAs] = useState("asc");

    const apiUsers = 'http://172.19.100.10:8080/api/users'

    const fetchData = useCallback(async () => {
        try {
          const config = {
            headers: {
              'Authorization': `Bearer ${token}`,
            },
          };
      
          const response = await axios.get(apiUsers, config);
          setUsers(response.data);
        } catch (error) {
          console.error('Error while fetching data:', error);
        }
      }, [token]);

    useEffect(() => {
        fetchData();
        setRefreshTable(false);
    }, [refreshTable, token, fetchData]);


    const handleHeaderClickWrapper = (header) => {
        handleHeaderClick(header, currentHeader, sortAs, setCurrentHeader, setSortAs);
    };

    const handleEditButton = (user) => {
        setSelectedUser({email: user.email, role: user.role});
        setEditUserPopupButton(true);
    }
    
    return(
        <div>
            <h2>Lista osób uprawnionych:</h2>
            <table className="UserTable">
                <thead>
                    <tr>
                        {generateTableHeaders(headers, currentHeader, sortAs, handleHeaderClickWrapper)}
                        <th><button onClick={() => setAddUserPopupButton(true)} className="TableButton">Dodaj</button></th>
                    </tr>
                </thead>
                <tbody>
                    {sortUsers(users, currentHeader, sortAs).map( (user, index) => (
                        <tr key={index} className="UserRow">
                            <td>{user.email}</td>
                            <td>{user.role}</td>
                            <td><button onClick={() => handleEditButton(user)} className="TableButton">Edytuj</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {AddUserPopupButton && (
                <AddUserPopup trigger={AddUserPopupButton} setTrigger={setAddUserPopupButton} refreshTable={setRefreshTable} token={token}></AddUserPopup>
            )}
            {EditUserPopupButton && (
                <EditUserPopup trigger={EditUserPopupButton} setTrigger={setEditUserPopupButton} user={selectedUser} refreshTable={setRefreshTable} token={token}></EditUserPopup>
            )}
        </div>
    )
}