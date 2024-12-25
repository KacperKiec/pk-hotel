import { useState } from "react";
import NavBar from "../common/NavBar";
import "./AdminPanel.css";
import AddHotel from "./Hotel/AddHotel";
import { DeleteHotel } from "./Hotel/DeleteHotel";
import AddRoom from "./Room/AddRoom";
import UpdateHotel from "./Hotel/UpdateHotel";
import DeleteRoom from "./Room/DeleteRoom";

export const AdminPanel = () => {
  const [activeTab, setActiveTab] = useState(1);
  const tabsNames = [
    "Add Hotel",
    "Delete Hotel",
    "Update Hotel",
    "Add/Update Room",
    "Delete Room",
  ];
  return (
    <div className="user-panel-container">
      <NavBar
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        tabsNames={tabsNames}
      />
      {activeTab === 1 && <AddHotel />}
      {activeTab === 2 && <DeleteHotel />}
      {activeTab === 3 && <UpdateHotel />}
      {activeTab === 4 && <AddRoom />}
      {activeTab === 5 && <DeleteRoom />}
    </div>
  );
};
