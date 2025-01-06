import "../AdminPanel.css";
import { Convenience } from "./AddRoom";

interface ConveniencesProps {
  conveniences: Convenience[];
  onClick: (e: any, name: string) => void;
}

export const Conveniences = ({ conveniences, onClick }: ConveniencesProps) => {
  return (
    <div className="conveniences-container">
      {conveniences.map(
        (element, index) =>
          element.name !== "" && (
            <span
              key={index}
              className="convenience-block"
              onClick={(e) => onClick(e, element.name)}
            >
              {element.name}
            </span>
          )
      )}
    </div>
  );
};

export default Conveniences;
