import React from "react";
import "../AdminPanel.css";

interface InputWithLabelProps {
  fieldName: string;
  label: string;
  type: string;
  value: string; // Support for file arrays
  onChange: (
    event: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >,
    field?: any
  ) => void;
  onClick?: (e: any) => void;
}

export const InputWithLabel = ({
  fieldName,
  label,
  type,
  value,
  onChange,
  onClick,
}: InputWithLabelProps) => {
  // Render the input based on the type
  let inputElement;

  if (type === "select") {
    inputElement = (
      <select
        name={fieldName}
        id={fieldName}
        value={value}
        onChange={onChange}
        className="add_room__input"
      >
        <option value="LOW">Low</option>
        <option value="AVERAGE">Average</option>
        <option value="HIGH">High</option>
      </select>
    );
  } else if (type === "textarea") {
    inputElement = (
      <textarea
        name={fieldName}
        id={fieldName}
        cols={80}
        rows={6}
        value={value as string}
        onChange={onChange}
        className="add-room__input"
      />
    );
  } else if (type === "file") {
    inputElement = (
      <input
        name={fieldName}
        type={type}
        onChange={onChange} // Handle file change
        className="add-room__input"
        multiple
      />
    );
  } else {
    inputElement = (
      <input
        name={fieldName}
        type={type}
        value={value as string}
        onChange={onChange}
        className="add-room__input"
      />
    );
  }

  return (
    <div key={fieldName} className="add-room__field">
      <label className="add-room__label">{label}</label>
      <div className="add-room-inputs">
        {inputElement}
        {fieldName === "price" && <label className="currency">zł</label>}
        {onClick !== undefined && (
          <button type="button" className="conveniences-btn" onClick={onClick}>
            Add
          </button>
        )}
      </div>
    </div>
  );
};
