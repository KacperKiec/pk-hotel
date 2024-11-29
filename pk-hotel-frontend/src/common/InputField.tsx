import React from 'react';

interface InputFieldProps {
  type: string;
  name: string;
  id: string;
  placeholder: string;
  iconClass: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  value: string | number;
}

const InputField: React.FC<InputFieldProps> = ({ type, name, id, placeholder, iconClass, onChange, value }) => {
  return (
    <div 
      className="inputFieldsWrapper" 
      style={{position: "relative"}}
    >
      <input
        className="inputField"
        type={type}
        name={name}
        id={id}
        placeholder={placeholder}
        required
        onChange={onChange}
        value={value}
      />
      <i 
        className={iconClass} 
        style={{
          position: "absolute", 
          top: "19px"}}
      >
      </i>
    </div>
  );
};

export default InputField;
