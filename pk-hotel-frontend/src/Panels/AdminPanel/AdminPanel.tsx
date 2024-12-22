import { useState } from "react";
import NavBar from "../common/NavBar";
import "./AdminPanel.css";
import AddHotel from "./Hotel/AddHotel";
import { DeleteHotel } from "./Hotel/DeleteHotel";
import AddRoom from "./Room/AddRoom";

export const AdminPanel = () => {
  const [activeTab, setActiveTab] = useState(1);
  const tabsNames = ["Add Hotel", "Delete Hotel", "Add Room"];
  return (
    <div className="user-panel-container">
      <NavBar
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        tabsNames={tabsNames}
      />
      {activeTab === 1 && <AddHotel />}
      {activeTab === 2 && <DeleteHotel />}
      {activeTab === 3 && <AddRoom />}
    </div>
  );
};
