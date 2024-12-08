import { Dispatch } from "react";
import "./NavBar.css"; 

interface NavBarProps {
    activeTab: number;
    setActiveTab: Dispatch<number>;
}
  
const NavBar = ({activeTab, setActiveTab} : NavBarProps) => {

  return (
    <div id="main">
      <nav className="navbar navbar-default">
        <ul className="nav navbar-nav">
          <li className={activeTab === 1 ? "active" : ""}>
            <button onClick={() => setActiveTab(1)}>User Details</button>
          </li>
          <li className={activeTab === 2 ? "active" : ""}>
            <button onClick={() => setActiveTab(2)}>Reservations History</button>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default NavBar;
