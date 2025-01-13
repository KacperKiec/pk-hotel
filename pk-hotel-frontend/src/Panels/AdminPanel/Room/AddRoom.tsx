import React, { useState } from "react";
import "../AdminPanel.css";
import { InputWithLabel } from "./InputWithLabel";
import Slider from "./Slider/Slider";
import Conveniences from "./Conveniences";
import { Room, Standard, transformRoom } from "../../../Rooms/Room";
import {
  addConvenienceAndAssignToRoom,
  addImageApi,
  addImageProps,
  addRoomApi,
  findRoomApi,
  removeConvenienceApi,
  removeRoomConveniencesApi,
  updateRoomApi,
} from "../../../Api/Api";

export interface Images {
  id: number | undefined;
  path: string;
}

export interface Convenience {
  id: number | undefined;
  name: string;
}

interface RoomData {
  hotelId: string;
  roomNr: string;
  standard: string;
  places: string;
  description: string;
  price: string;
  images: Images[];
  conveniences: Convenience[];
}

const AddRoom = () => {
  const [roomData, setRoomData] = useState<RoomData>({
    hotelId: "",
    roomNr: "",
    standard: "",
    places: "",
    description: "",
    price: "",
    images: [],
    conveniences: [],
  });

  const [currentConvenience, setCurrentConvenience] = useState<Convenience>({
    id: undefined,
    name: "",
  });

  const [error, setError] = useState("");
  const [findRoomError, setFindRoomError] = useState("");
  const [foundRoom, setFoundRoom] = useState<RoomData | undefined>(undefined);
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

    const images = filePaths.map((element) => ({
      id: undefined,
      path: element,
    }));

    if (isValid) {
      setRoomData((prev) => ({
        ...prev,
        images: images,
      }));
    }
  };

  const handleConveniencesChange = (value: string) => {
    setCurrentConvenience((prev) => ({
      ...prev,
      name: value,
    }));
  };

  const handleAddConvenience = (e: React.MouseEvent<HTMLSpanElement>) => {
    if (currentConvenience.name === "") return;
    setRoomData((prev) => ({
      ...prev,
      conveniences: [...prev.conveniences, currentConvenience],
    }));
    setCurrentConvenience({ id: undefined, name: "" });
  };

  const handleFindRoom = async (e: React.MouseEvent<HTMLButtonElement>) => {
    setFoundRoom(undefined);
    const response = await findRoomApi(
      Number(roomData.hotelId),
      Number(roomData.roomNr)
    );

    if (response.status === -1) {
      setFindRoomError("This room isn't existing. It will be created instead.");
      setRoomData((prev) => ({
        ...prev,
        standard: "",
        places: "",
        price: "",
        description: "",
        images: [],
        conveniences: [],
      }));
      return;
    }

    const foundRoomLocal = response.data; // Local variable scoped to this block
    if (!foundRoomLocal) return;

    setRoomData((prev) => ({
      ...prev,
      standard: foundRoomLocal.standard,
      places: foundRoomLocal.places,
      description: foundRoomLocal.description,
      price: foundRoomLocal.price,
      images: foundRoomLocal.images,
      conveniences: foundRoomLocal.conveniences,
    }));
    setFoundRoom(foundRoomLocal);
    console.log(foundRoom);
    return;
  };

  const handleDeleteConvenience = (
    e: React.MouseEvent<HTMLSpanElement>,
    name: string
  ) => {
    setRoomData((prev) => ({
      ...prev,
      conveniences: prev.conveniences.filter(
        (element) => element.name !== name
      ),
    }));
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
    setFindRoomError("");
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
  };

  const deleteConveniencesFromDb = () => {
    console.log(foundRoom);
    if (!foundRoom) return;
    const conveniecesIds: number[] = [];
    foundRoom.conveniences.forEach((element) => {
      if (!roomData.conveniences.includes(element)) {
        if (element.id) {
          conveniecesIds.push(element.id);
        }
      }
    });

    if (conveniecesIds.length > 0)
      removeRoomConveniencesApi(
        Number(roomData.roomNr),
        Number(roomData.hotelId),
        conveniecesIds
      );
  };

  const addConveniencesToDb = async (room: Room) => {
    const addedConveniences: Convenience[] = [];
    // Add conveniences and assign to room
    for (const convenience of roomData.conveniences) {
      if (foundRoom && foundRoom.conveniences.includes(convenience)) continue;

      console.log(convenience.name);

      const convenienceResponse = await addConvenienceAndAssignToRoom(
        convenience.name,
        transformRoom(room)
      );

      if (convenienceResponse.status !== 201) {
        setError(
          convenienceResponse.message ||
            `Error while adding convenience: ${convenience.name}`
        );
        return;
      }

      if (convenienceResponse.convenience)
        addedConveniences.push(convenienceResponse.convenience);
    }
  };

  const deleteImagesFromDb = () => {
    if (!foundRoom) return;
    const imagesIds: number[] = [];
    foundRoom.images.forEach((element) => {
      if (!roomData.images.includes(element)) {
        if (element.id) {
          imagesIds.push(element.id);
        }
      }
    });

    if (imagesIds.length > 0)
      removeRoomConveniencesApi(
        Number(roomData.roomNr),
        Number(roomData.hotelId),
        imagesIds
      );
  };

  const addImagesToDb = async (room: Room) => {
    const addedImages: Images[] = [];
    // Add conveniences and assign to room
    for (const image of roomData.images) {
      if (foundRoom && foundRoom.images.includes(image)) continue;
      addedImages.push(image);
    }
    const response = await addImageApi({
      room: transformRoom(room),
      images: addedImages,
    });
    if (response.message) setError(response.message);
  };

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Check for empty fields
    const emptyFields = Object.keys(roomData).filter((key) => {
      const field = key as keyof typeof roomData;
      if (field === "standard") return false;
      if (field === "images") return roomData.images.length === 0;
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
    setFindRoomError("");

    const room: Room = new Room({
      roomNr: Number(roomData.roomNr),
      hotelId: Number(roomData.hotelId),
      standard: roomData.standard as Standard,
      places: Number(roomData.places),
      price: Number(roomData.price),
      description: roomData.description,
    });

    const response = await addRoomApi(transformRoom(room));

    if (response.message) {
      if (response.status !== 409) {
        setError(response.message);
        return;
      }
    }
    if (response.status === 409) {
      const updateResponse = await updateRoomApi(transformRoom(room));
      if (updateResponse.message) {
        setError(updateResponse.message);
        return;
      }
    }

    deleteConveniencesFromDb();
    addConveniencesToDb(room);

    addImagesToDb(room);

    setFindRoomError("");
    setConfirmMessage("Room and conveniences added successfully!");
    setFoundRoom(undefined);
    // Reset roomData after submission
    setRoomData({
      hotelId: "",
      roomNr: "",
      standard: "",
      places: "",
      price: "",
      description: "",
      images: [],
      conveniences: [],
    });
  };

  return (
    <div className="admin-panel-container">
      <h1 className="admin-panel-h1">Add/Update Room</h1>
      <form className="admin-panel-form" onSubmit={handleSubmit}>
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

        <div className="submit-errors">
          <span>
            {findRoomError !== "" && (
              <div className="admin-panel-error">{findRoomError}</div>
            )}
          </span>
          <button type="button" className="room-btn" onClick={handleFindRoom}>
            Find Room
          </button>
        </div>

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

        {roomData.images.length !== 0 && (
          <Slider
            images={roomData.images}
            setRoomData={setRoomData}
            room={
              new Room({
                roomNr: Number(roomData.roomNr),
                hotelId: Number(roomData.hotelId),
                standard: roomData.standard as Standard,
                places: Number(roomData.places),
                price: Number(roomData.price),
                description: roomData.description,
              })
            }
          />
        )}

        <InputWithLabel
          fieldName="conveniences"
          label="Conveniences"
          value={currentConvenience.name}
          onChange={handleChange}
          type="text"
          onClick={handleAddConvenience}
        />

        <Conveniences
          conveniences={roomData.conveniences.filter(
            (c) => typeof c.name === "string" && c.name.trim() !== ""
          )}
          onClick={handleDeleteConvenience}
        />

        <div className="submit-errors">
          <span>
            {error && <div className="admin-panel-error">{error}</div>}
            {confirmMessage && (
              <div className="positive-response">{confirmMessage}</div>
            )}
          </span>
          <button type="submit" className="room-btn">
            Save
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddRoom;
