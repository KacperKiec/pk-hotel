import "../AdminPanel.css";
import { Extra } from "./Extra";

interface ExtrasProps {
  extras: Extra[];
  onClick: (e: any, name: string) => void;
}

const Extras = ({ extras, onClick }: ExtrasProps) => {
  return (
    <div className="conveniences-container">
      {extras.map(
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

export default Extras;
