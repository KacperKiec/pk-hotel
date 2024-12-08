import React, { Dispatch, SetStateAction, useState } from 'react'
import NavBar from '../common/NavBar'
import { User } from '../../Users/User';
import UserDetails from '../common/UserDetails';
import { ReservationHistory } from '../common/ReservationHistory';
import './UserPanel.css'

interface UserPanelProps {
    loggedUser: User;
    setLoggedUser: Dispatch<SetStateAction<User | undefined>>;
}

export const UserPanel = ({loggedUser, setLoggedUser}: UserPanelProps) => {
    const [activeTab, setActiveTab] = useState(1);
  return (
    <div className="user-panel-container">
        <NavBar activeTab={activeTab} setActiveTab={setActiveTab}/>
        {activeTab === 1 && <UserDetails loggedUser={loggedUser} setLoggedUser={setLoggedUser}/>}
        {activeTab === 2 && <ReservationHistory></ReservationHistory>}
    </div>
    
  );
}
