import React, { Dispatch, SetStateAction, useState } from 'react'
import NavBar from '../common/NavBar'
import { ReservationHistory } from '../common/ReservationHistory';
import './UserPanel.css'

export const UserPanel = () => {
    const [activeTab, setActiveTab] = useState(1);
  return (
    <div className="user-panel-container">
        <NavBar activeTab={activeTab} setActiveTab={setActiveTab}/>
        {activeTab === 1 && <div>Admin</div>}
        {activeTab === 2 && <ReservationHistory></ReservationHistory>}
    </div>
    
  );
}
