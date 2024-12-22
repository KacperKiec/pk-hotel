import React from "react";
import "./Slider.css";

interface RoomImageInterface {
  imageUrl: string;
  setRoomData: React.Dispatch<
    React.SetStateAction<{
      hotelId: string;
      roomNr: string;
      standard: string;
      places: string;
      price: string;
      description: string;
      imagesUrl: string[];
      conveniences: string[];
    }>
  >;
}

export const RoomImage = ({ imageUrl, setRoomData }: RoomImageInterface) => {
  const onClick = () => {
    setRoomData((prevState) => ({
      ...prevState, // Keep the previous state values unchanged
      imagesUrl: prevState.imagesUrl.filter((url) => url !== imageUrl), // Remove the imageUrl
    }));
  };

  return (
    <div className="room-image-container">
      <img src={imageUrl} alt="Room image" className="room-image-add" />
      <button className="delete-image-button" type="button" onClick={onClick}>
        Delete
      </button>
    </div>
  );
};
