import React from "react";
import "../AdminPanel.css";

interface ConveniencesProps {
  conveniences: string[];
  onClick: (e: any, index: number) => void;
}

export const Conveniences = ({ conveniences, onClick }: ConveniencesProps) => {
  return (
    <div className="conveniences-container">
      {conveniences.map(
        (element, index) =>
          element !== "" && (
            <span
              key={index}
              className="convenience-block"
              onClick={(e) => onClick(e, index)}
            >
              {element}
            </span>
          )
      )}
    </div>
  );
};

export default Conveniences;
