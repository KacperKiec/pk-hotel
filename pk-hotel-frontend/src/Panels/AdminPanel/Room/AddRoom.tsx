import React, { useState } from "react";
import "../AdminPanel.css";
import { InputWithLabel } from "./InputWithLabel";
import Slider from "./Slider";
import Conveniences from "./Conveniences";
import { Room, Standard, transformRoom } from "../../../Rooms/Room";
import {
  addConvenienceAndAssignToRoom,
  addImageApi,
  addImageProps,
  addRoomApi,
  Response,
} from "../../../Api/Api";

const AddRoom = () => {
  const [roomData, setRoomData] = useState({
    hotelId: "",
    roomNr: "",
    standard: "",
    places: "",
    description: "",
    price: "",
    imagesUrl: [""],
    conveniences: [""],
  });

  const [currentConvenience, setCurrentConvenience] = useState("");

  const [error, setError] = useState("");
  const [confirmMessage, setConfirmMessage] = useState("");

  // Type guard to check if the event target is a file input
  const isFileInput = (element: HTMLElement): element is HTMLInputElement => {
    return element instanceof HTMLInputElement && element.type === "file";
  };

  const handleFiles = (files: FileList) => {
    const validExtensions = ["jpg", "jpeg", "png"];
    const filePaths: string[] = [];
    let isValid = true;

    // Loop through the selected files
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      const fileExtension = file.name.split(".").pop()?.toLowerCase();

      // Check if the file extension is valid
      if (fileExtension && validExtensions.includes(fileExtension)) {
        filePaths.push(`/assets/${file.name}`);
      } else {
        isValid = false;
        setError("Invalid file!");
        break;
      }
    }

    if (isValid) {
      setRoomData((prev) => ({
        ...prev,
        imagesUrl: filePaths,
      }));
      console.log(filePaths);
    }
  };

  const handleConveniencesChange = (value: string) => {
    setCurrentConvenience(value);
    console.log(currentConvenience);
  };

  const handleAddConvenience = (e: React.MouseEvent<HTMLSpanElement>) => {
    setRoomData((prev) => ({
      ...prev,
      conveniences: [...prev.conveniences, currentConvenience],
    }));
    setCurrentConvenience("");
    console.log(roomData.conveniences);
  };

  const handleDeleteConvenience = (
    e: React.MouseEvent<HTMLSpanElement>,
    conIdx: number
  ) => {
    setRoomData((prev) => ({
      ...prev,
      conveniences: prev.conveniences.filter(
        (value, index) => index !== conIdx
      ),
    }));
    console.log(roomData.conveniences);
  };

  // Handle change for all inputs
  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >
  ) => {
    const { target } = e;
    const { name, value } = target;
    setError("");
    setConfirmMessage("");

    // Check if it's a file input and has files
    if (isFileInput(target) && target.files) {
      handleFiles(target.files);
    } else if (name === "conveniences") {
      handleConveniencesChange(value);
    } else {
      setRoomData((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
    console.log(roomData.description);
  };

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Check for empty fields
    const emptyFields = Object.keys(roomData).filter((key) => {
      const field = key as keyof typeof roomData;
      if (field === "standard") return false;
      if (field === "imagesUrl")
        return roomData.imagesUrl[0] === "" || roomData.imagesUrl.length === 0;
      return roomData[field].length === 0;
    });

    // If there are empty fields, set error and return early
    if (emptyFields.length > 0) {
      const field = emptyFields[0];
      setConfirmMessage("");
      setError(
        `${field.at(0)?.toUpperCase() + field.slice(1)} field must be filled.`
      );
      return;
    }

    // Proceed with the API call or form submission
    setError("");

    const room: Room = new Room({
      roomNr: Number(roomData.roomNr),
      hotelId: Number(roomData.hotelId),
      standard: roomData.standard as Standard,
      places: Number(roomData.places),
      price: Number(roomData.price),
      imagesUrl: roomData.imagesUrl,
      description: roomData.description,
      reviews: 0.0,
      conveniences: [], // Start with an empty array; conveniences will be added later
      name: "",
    });

    const response: Response = await addRoomApi(transformRoom(room));

    if (response.status !== 201) {
      setError(response.message || "Error while adding room");
      return;
    }

    const roomImages: addImageProps = {
      room: transformRoom(room),
      image: room.imagesUrl,
    };

    const imageResponse: Response = await addImageApi(roomImages);
    if (imageResponse.status !== 201) {
      setError(imageResponse.message || "Error while adding room");
      return;
    }

    // Add conveniences and assign to room
    for (const convenienceName of roomData.conveniences) {
      const convenienceResponse: Response = await addConvenienceAndAssignToRoom(
        convenienceName,
        transformRoom(room)
      );

      if (convenienceResponse.status !== 201) {
        setError(
          convenienceResponse.message ||
            `Error while adding convenience: ${convenienceName}`
        );
        return;
      }
    }

    setConfirmMessage("Room and conveniences added successfully!");
    // Reset roomData after submission
    setRoomData({
      hotelId: "",
      roomNr: "",
      standard: "",
      places: "",
      price: "",
      description: "",
      imagesUrl: [""],
      conveniences: [""],
    });
  };

  return (
    <div className="add-room-container">
      <h1 className="add-room-h1">Add Room</h1>
      <form className="add-room-form" onSubmit={handleSubmit}>
        <InputWithLabel
          fieldName="hotelId"
          label="Hotel ID"
          value={roomData.hotelId}
          onChange={handleChange}
          type="number"
        />

        <InputWithLabel
          fieldName="roomNr"
          label="Room Number"
          value={roomData.roomNr}
          onChange={handleChange}
          type="number"
        />

        <InputWithLabel
          fieldName="standard"
          label="Standard"
          value={roomData.standard}
          onChange={handleChange}
          type="select"
        />

        <InputWithLabel
          fieldName="places"
          label="Capacity"
          value={roomData.places}
          onChange={handleChange}
          type="number"
        />

        <InputWithLabel
          fieldName="description"
          label="Description"
          value={roomData.description}
          onChange={handleChange}
          type="textarea"
        />

        <InputWithLabel
          fieldName="price"
          label="Price"
          value={roomData.price}
          onChange={handleChange}
          type="number"
        />

        {/* File input for images */}
        <InputWithLabel
          fieldName="imagesUrl"
          label="Images"
          value=""
          onChange={handleChange}
          type="file"
        />

        {roomData.imagesUrl[0] !== "" && roomData.imagesUrl.length !== 0 && (
          <Slider imagesUrl={roomData.imagesUrl} setRoomData={setRoomData} />
        )}

        <InputWithLabel
          fieldName="conveniences"
          label="Conveniences"
          value={currentConvenience}
          onChange={handleChange}
          type="text"
          onClick={handleAddConvenience}
        />

        <Conveniences
          conveniences={roomData.conveniences}
          onClick={handleDeleteConvenience}
        />

        <div className="submit-errors">
          <span>
            {error && <div className="add-room-error">{error}</div>}
            {confirmMessage && (
              <div className="positive-response">{confirmMessage}</div>
            )}
          </span>
          <button type="submit" className="save-new-room-btn">
            Save
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddRoom;
