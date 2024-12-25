import React, { useState } from "react";
import "../AdminPanel.css";
import { removeRoomApi } from "../../../Api/Api";

const DeleteRoom = () => {
  const [hotelId, setHotelId] = useState("");
  const [roomNr, setRoomNr] = useState("");
  const [message, setMessage] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if(e.target.name === 'hotelId') setHotelId(e.target.value);
    if(e.target.name === 'roomNr') setRoomNr(e.target.value);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (hotelId === "") {
      setMessage("hotel id must be filled.");
      return;
    }
    if (roomNr === "") {
        setMessage("Room number must be filled.");
        return;
    }
    const response = await removeRoomApi(Number(hotelId), Number(roomNr));
    if (response.status !== 204) {
      setMessage(response.message || "Error while deleting the room.");
      return;
    }
    setMessage("room was removed.");
    setRoomNr("");
    setHotelId("");
  };

  return (
    <div className="admin-panel-container">
      <h1 className="admin-panel-h1">Delete room</h1>
      <form className="admin-panel-form" onSubmit={handleSubmit}>
        <div className="admin-panel__field">
          <label className="admin-panel__label">Hotel ID</label>
          <div>
            <input
              name="hotelId"
              type="number"
              value={hotelId}
              onChange={handleChange}
              className="admin-panel__input"
            />
          </div>
        </div>
        <div className="admin-panel__field">
          <label className="admin-panel__label">Room number</label>
          <div>
            <input
              name="roomNr"
              type="number"
              value={roomNr}
              onChange={handleChange}
              className="admin-panel__input"
            />
          </div>
        </div>
        <div className="submit-errors">
          <span>
            <div
              className={`admin-panel${
                message.includes("room was removed.")
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

export default DeleteRoom;