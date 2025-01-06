import "./Dropdown.css";

interface DropdownProps {
  activeTab: number;
  setActiveTab: React.Dispatch<React.SetStateAction<number>>;
  tabsNames: string[];
  baseOrder: number; // Base order for this dropdown's tabs
}

const Dropdown = ({ activeTab, setActiveTab, tabsNames, baseOrder }: DropdownProps) => {
  return (
    <div className="dropdown-content">
      <ul className="dropdown-list">
        {tabsNames.map((tabName, index) => {
          const order = baseOrder + index; // Unique order for each button
          return (
            <li key={order} className={activeTab === order ? "active" : ""}>
              <button onClick={() => setActiveTab(order)}>{tabName}</button>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default Dropdown;
