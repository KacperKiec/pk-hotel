import { Dispatch } from "react";
import "./NavBar.css"; 

interface NavBarProps {
  activeTab: number;
  setActiveTab: Dispatch<number>;
  tabsNames: string[];
}
  
const NavBar = ({activeTab, setActiveTab, tabsNames} : NavBarProps) => {
  return (
    <div id="main">
      <nav className="navbar navbar-default">
        <ul className="nav navbar-nav">
          { tabsNames.map((tabName, i) => {
            return (
              <li key={i+1} className={activeTab === i+1 ? "active" : ""}>
                <button onClick={() => setActiveTab(i+1)}>{tabName}</button>
              </li>
              )
            })
          }
        </ul>
      </nav>
    </div>
  );
};

export default NavBar;
