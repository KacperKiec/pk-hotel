import React, { Dispatch, SetStateAction, useState } from 'react'
import NavBar from '../common/NavBar'
import HotelPanel from './HotelPanel'
import './AdminPanel.css'

export const AdminPanel = () => {
  const [activeTab, setActiveTab] = useState(1);
  const tabsNames = ['Add Hotel', 'Rooms', 'Users'];
  return (
    <div className="user-panel-container">
        <NavBar activeTab={activeTab} setActiveTab={setActiveTab} tabsNames={tabsNames}/>
        {activeTab === 1 && <HotelPanel/>}
        {activeTab === 2 && <div>Rooms</div>}
    </div>
    
  );
}
