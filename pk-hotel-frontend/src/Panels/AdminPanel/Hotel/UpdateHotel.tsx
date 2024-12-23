import React, { useState } from "react";
import { Hotel, transfromHotel } from "../../../Hotel/Hotel";
import { updateHotelApi } from "../../../Api/Api";

const UpdateHotel = () => {
  const [hotelData, setHotelData] = useState({
    id: "",
    name: "",
    owner: "",
    country: "",
    city: "",
    address: "",
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    field: keyof typeof hotelData
  ) => {
    setHotelData({ ...hotelData, [field]: e.target.value });
  };

  const [error, setError] = useState("");
  const [confirmMessage, setConfirmMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Check all fields first
    const emptyFields = Object.keys(hotelData).filter((key) => {
      const field = key as keyof typeof hotelData;
      return hotelData[field].length === 0;
    });

    // If there are empty fields, set error and return early
    if (emptyFields.length > 0) {
      const field = emptyFields[0];
      setError(
        `${
          field.at(0)?.toUpperCase() + field.slice(1)
        } field have to be filled.`
      );
      return;
    }

    // Clear any previous errors
    setError("");

    // Proceed with API call
    const hotel: Hotel = new Hotel({
      name: hotelData.name,
      owner: hotelData.owner,
      country: hotelData.country,
      city: hotelData.city,
      address: hotelData.address,
    });
    const response = await updateHotelApi(
      transfromHotel(hotel),
      Number(hotelData.id)
    );

    if (response.status !== 200) {
      setError(response.message || "Error while updating hotel.");
      return;
    }

    setConfirmMessage("Hotel was updated.");
    setHotelData({
      id: "",
      name: "",
      owner: "",
      country: "",
      city: "",
      address: "",
    });
  };

  return (
    <div className="admin-panel-container">
      <h1 className="admin-panel-h1">Update hotel</h1>
      <form className="admin-panel-form" onSubmit={handleSubmit}>
        {Object.keys(hotelData).map((key) => {
          const field = key as keyof typeof hotelData;
          const label = field.charAt(0).toUpperCase() + field.slice(1); // Capitalize field name for the label
          return (
            <div key={field} className="admin-panel__field">
              <label className="admin-panel__label">{label}</label>
              <div className="admin-panel-inputs">
                <input
                  name={field}
                  type="text"
                  value={hotelData[field]}
                  onChange={(e) => handleChange(e, field)}
                  className="admin-panel__input"
                />
              </div>
            </div>
          );
        })}
        <div className="submit-errors">
          <span>
            {error.length > 0 && (
              <div className="admin-panel-error">{error}</div>
            )}
            {confirmMessage === "Hotel was updated." ? (
              <div className="positive-response">{confirmMessage}</div>
            ) : (
              <div className="admin-panel-error">{confirmMessage}</div>
            )}
          </span>
          <button type="submit" className="save-new-hotel-btn">
            Save
          </button>
        </div>
      </form>
    </div>
  );
};

export default UpdateHotel;
