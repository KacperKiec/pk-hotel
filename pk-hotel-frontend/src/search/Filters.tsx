import React, { useState } from "react";

interface FiltersProps {
  selected: number;
  setSelected: React.Dispatch<React.SetStateAction<number>>
}

const Filters = ({selected, setSelected}: FiltersProps) => {
  const handleChange = (index: number) => {
    if (index !== selected) {
      setSelected(index);
    }
  };

  return (
    <div className="filters-container">
      <span>
        <strong>Standard:</strong>
      </span>
      <hr />
      <div className="input-group vertical">
        <div className="row">
          <input
            type="checkbox"
            name="low"
            checked={selected === 1}
            onChange={() => handleChange(1)}
          />
          <label htmlFor="low">Low</label>
        </div>
        <div className="row">
          <input
            type="checkbox"
            name="average"
            checked={selected === 2}
            onChange={() => handleChange(2)}
          />
          <label htmlFor="average">Average</label>
        </div>
        <div className="row">
          <input
            type="checkbox"
            name="high"
            checked={selected === 3}
            onChange={() => handleChange(3)}
          />
          <label htmlFor="high">High</label>
        </div>
      </div>
    </div>
  );
};

export default Filters;
