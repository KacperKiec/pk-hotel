import React from "react";
import "./Slider/Slider.css";
import { Convenience, Images } from "./AddRoom";
import { removeImagesApi } from "../../../Api/Api";
import { Room, transformRoom } from "../../../Rooms/Room";

interface RoomImageInterface {
  image: Images;
  room: Room;
  setRoomData: React.Dispatch<
    React.SetStateAction<{
      hotelId: string;
      roomNr: string;
      standard: string;
      places: string;
      price: string;
      description: string;
      images: Images[];
      conveniences: Convenience[];
    }>
  >;
}

export const RoomImage = ({ image, setRoomData, room }: RoomImageInterface) => {
  const onClick = () => {
    removeImagesApi(transformRoom(room), [image]);
    setRoomData((prevState) => ({
      ...prevState, // Keep the previous state values unchanged
      images: prevState.images.filter((element) => image.path !== element.path), // Remove the imageUrl
    }));
  };

  return (
    <div className="room-image-container">
      <img src={image.path} alt="Room-image" className="room-image-add" />
      <button className="delete-image-button" type="button" onClick={onClick}>
        Delete
      </button>
    </div>
  );
};
