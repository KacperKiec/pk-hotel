import { Dispatch, SetStateAction, useState } from "react";
import NavBar from "./NavBar";
import { User } from "../../Users/User";
import UserDetails from "./UserDetails";
import ReservationHistory from "./ReservationHistory";
import "./UserPanel.css";

interface UserPanelProps {
  loggedUser: User;
  setLoggedUser: Dispatch<SetStateAction<User | undefined>>;
}

export const UserPanel = ({ loggedUser, setLoggedUser }: UserPanelProps) => {
  const [activeTab, setActiveTab] = useState(1);
  const tabsNames = ["User Details", "Reservation history"];
  return (
    <div className="user-panel-container">
      <NavBar
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        tabsNames={tabsNames}
      />
      {activeTab === 1 && (
        <UserDetails loggedUser={loggedUser} setLoggedUser={setLoggedUser} />
      )}
      {activeTab === 2 && <ReservationHistory loggedUser={loggedUser} />}
    </div>
  );
};
