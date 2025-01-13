import { SetStateAction, useState } from "react";
import "./AdminPanel.css";
import AddHotel from "./Hotel/AddHotel";
import { DeleteHotel } from "./Hotel/DeleteHotel";
import AddRoom from "./Room/AddRoom";
import UpdateHotel from "./Hotel/UpdateHotel";
import DeleteRoom from "./Room/DeleteRoom";
import Dropdown from "../common/Dropdown";
import Extras from "./Extras/Extras";
import { User } from "../../Users/User";
import { useNavigate } from "react-router-dom";
import { logoutAPI } from "../../Api/Api";

interface AdminPanelProps {
  loggedUser: User | undefined;
  setLoggedUser: React.Dispatch<SetStateAction<User | undefined>>;
}

export const AdminPanel = ({ loggedUser, setLoggedUser }: AdminPanelProps) => {
  const [activeTab, setActiveTab] = useState(1);

  const tabsNamesHotel = ["Add Hotel", "Delete Hotel", "Update Hotel"];
  const tabsNamesRoom = ["Add/Update Room", "Delete Room"];
  const navigator = useNavigate();

  const handleLogout = async (e: any) => {
    setLoggedUser(undefined);
    try {
      await logoutAPI();
      navigator("/");
    } catch (error) {
      console.log("logout error!");
    }
  };

  return (
    <div className="user-panel-container ">
      <nav className="navbar">
        <div className="dropdown">
          <button className="dropdown-btn">Hotel</button>
          <Dropdown
            activeTab={activeTab}
            setActiveTab={setActiveTab}
            tabsNames={tabsNamesHotel}
            baseOrder={1} // Base order for Hotel tabs
          />
        </div>
        <div className="dropdown">
          <button className="dropdown-btn">Room</button>
          <Dropdown
            activeTab={activeTab}
            setActiveTab={setActiveTab}
            tabsNames={tabsNamesRoom}
            baseOrder={4} // Base order for Room tabs
          />
        </div>
        <div className="dropdown">
          <button
            className="extras-btn dropdown-btn"
            onClick={() => {
              setActiveTab(6);
            }}
          >
            Extras
          </button>
        </div>
        <div className="dropdown">
          <button className="logout-btn dropdown-btn" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </nav>
      {activeTab === 1 && <AddHotel />}
      {activeTab === 2 && <DeleteHotel />}
      {activeTab === 3 && <UpdateHotel />}
      {activeTab === 4 && <AddRoom />}
      {activeTab === 5 && <DeleteRoom />}
      {activeTab === 6 && <Extras />}
    </div>
  );
};
