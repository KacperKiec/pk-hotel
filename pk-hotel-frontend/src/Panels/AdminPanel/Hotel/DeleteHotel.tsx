import React, { useState } from "react";
import "../AdminPanel.css";
import { removeHotelApi } from "../../../Api/Api";

export const DeleteHotel = () => {
  const [hotelId, setHotelId] = useState("");
  const [message, setMessage] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setHotelId(e.target.value);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (hotelId === "") {
      setMessage("Id must be filled.");
      return;
    }
    const response = await removeHotelApi(Number(hotelId));
    if (response.status !== 204) {
      setMessage(response.message || "Error while deleting the hotel.");
      return;
    }
    setMessage("Hotel was removed.");
    setHotelId("");
  };

  return (
    <div className="admin-panel-container">
      <h1 className="admin-panel-h1">Delete hotel</h1>
      <form className="admin-panel-form" onSubmit={handleSubmit}>
        <div className="admin-panel__field">
          <label className="admin-panel__label">ID</label>
          <div>
            <input
              name="id"
              type="number"
              value={hotelId}
              onChange={handleChange}
              className="admin-panel__input"
            />
          </div>
        </div>
        <div className="submit-errors">
          <span>
            <div
              className={`admin-panel${
                message.includes("Hotel was removed.")
                  ? " positive-message"
                  : "-error"
              }`}
            >
              {message}
            </div>
          </span>
          <button type="submit" className="admin-panel-btn">
            Save
          </button>
        </div>
      </form>
    </div>
  );
};
