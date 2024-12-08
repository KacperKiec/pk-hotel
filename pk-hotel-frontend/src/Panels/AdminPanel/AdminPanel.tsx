import React, { Dispatch, SetStateAction, useState } from 'react'
import NavBar from '../common/NavBar'
import './AdminPanel.css'
import AddHotel from './AddHotel'
import { DeleteHotel } from './DeleteHotel'

export const AdminPanel = () => {
  const [activeTab, setActiveTab] = useState(1);
  const tabsNames = ['Add Hotel', 'Delete Hotel', 'Users'];
  return (
    <div className="user-panel-container">
        <NavBar activeTab={activeTab} setActiveTab={setActiveTab} tabsNames={tabsNames}/>
        {activeTab === 1 && <AddHotel/>}
        {activeTab === 2 && <DeleteHotel/>}
    </div>
    
  );
}
