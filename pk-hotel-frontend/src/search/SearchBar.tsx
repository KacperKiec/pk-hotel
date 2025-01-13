import React, { useState, useEffect, useRef } from "react";
import "./SearchBar.css";
import DatePicker from "./DatePicker";
import { PeoplePicker } from "./PeoplePicker";
import { getHotelsFromCity, getRoomsWithFilters } from "../Api/Api";
import dayjs from "dayjs";
import { HotelDTO } from "../Hotel/Hotel";
import { Room } from "../Rooms/Room";

export type image = {
  id: number;
  path: string;
};

export const getImagesUrl = (images: image[]) => {
  const imagesUrl: string[] = [];
  images.forEach((element) => {
    imagesUrl.push(element.path);
  });
  return imagesUrl;
};

interface SearchBarProps {
  standard: number;
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>;
  setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  arrivalDate: string;
  departureDate: string;
  setArrivalDate: React.Dispatch<React.SetStateAction<string>>;
  setDepartureDate: React.Dispatch<React.SetStateAction<string>>;
}

export const SearchBar = ({
  standard,
  setRooms,
  setLoading,
  arrivalDate,
  departureDate,
  setArrivalDate,
  setDepartureDate,
}: SearchBarProps) => {
  const [isDatePickerVisible, setDatePickerVisibility] = useState(false);
  const [isPeopleCountVisible, setPeopleCountVisibility] = useState(false);
  const [error, setError] = useState("");

  const [city, setCity] = useState("");
  const [adults, setAdults] = useState(2);
  const [children, setChildren] = useState(0);

  const dateButtonRef = useRef<HTMLButtonElement>(null);
  const peopleButtonRef = useRef<HTMLButtonElement>(null);
  const datePickerRef = useRef<HTMLDivElement>(null);
  const peoplePickerRef = useRef<HTMLDivElement>(null);

  const handleDateClick = (e: any) => {
    e.preventDefault();
    setDatePickerVisibility((prev) => !prev);
    setPeopleCountVisibility(false); // Hide PeoplePicker if open
  };

  const handlePeopleClick = (e: any) => {
    e.preventDefault();
    setPeopleCountVisibility((prev) => !prev);
    setDatePickerVisibility(false); // Hide DatePicker if open
  };

  const handleClickOutside = (e: any) => {
    // Check if the click is outside DatePicker and PeoplePicker elements
    if (
      dateButtonRef.current &&
      !dateButtonRef.current.contains(e.target) &&
      datePickerRef.current &&
      !datePickerRef.current.contains(e.target)
    ) {
      setDatePickerVisibility(false);
    }

    if (
      peopleButtonRef.current &&
      !peopleButtonRef.current.contains(e.target) &&
      peoplePickerRef.current &&
      !peoplePickerRef.current.contains(e.target)
    ) {
      setPeopleCountVisibility(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    if (name === "arrivalDate") setArrivalDate(value);
    if (name === "departureDate") setDepartureDate(value);
    if (name === "adults") setAdults(Number(value));
    if (name === "children") setChildren(Number(value));
    if (name === "city") setCity(value);
  };

  const handleSumbit = async (e: React.FormEvent) => {
    e.preventDefault();

    let standardString = "";
    switch (standard) {
      case 1:
        standardString = "LOW";
        break;
      case 2:
        standardString = "AVERAGE";
        break;
      case 3:
        standardString = "HIGH";
        break;
      case 4:
        standardString = "";
        break;
      default:
        standardString = "ANY";
        break;
    }

    setLoading(true);

    const response = await getRoomsWithFilters({
      standard: standardString,
      startDate: arrivalDate,
      endDate: departureDate,
      places: adults + children,
      city,
      page: 0,
    });

    setTimeout(() => {
      setLoading(false);
    }, 600);

    if (!response.data) return;

    setRooms([]);

    response.data.forEach((element) => {
      setRooms((prev) => [
        ...prev,
        new Room({
          roomNr: element.roomNr,
          standard: element.standard,
          places: element.places,
          description: element.description,
          price: element.price,
          imagesUrl: getImagesUrl(element.images),
          reviews: element.rating,
          conveniences: element.conveniences,
          hotelName: element.hotelName,
        }),
      ]);
    });
  };

  return (
    <div className="form-container">
      <form className="input-group horizontal" onSubmit={handleSumbit}>
        <div className="container">
          <div className="row">
            <input
              type="text"
              id="place"
              name="city"
              placeholder="Where are you going?"
              className="col-sm-6 place"
              onChange={onInputChange}
              value={city}
            />
            <div className="button-group horizontal col-sm">
              <button onClick={handleDateClick} ref={dateButtonRef}>
                <span className="icon-calendar icons"></span>
              </button>
              <button onClick={handlePeopleClick} ref={peopleButtonRef}>
                <span className="icon-user icons"></span>
              </button>
            </div>
            <button className="search-button" type="submit">
              <span className="icon-search inverse icons"></span>
            </button>
          </div>
        </div>
        {isDatePickerVisible && (
          <div className="date" ref={datePickerRef}>
            <DatePicker
              arrivalDate={arrivalDate}
              departureDate={departureDate}
              onChange={onInputChange}
            />
          </div>
        )}
        {isPeopleCountVisible && (
          <div className="date people" ref={peoplePickerRef}>
            <PeoplePicker
              adultsValue={adults}
              childrenValue={children}
              onChange={onInputChange}
            />
          </div>
        )}
      </form>
    </div>
  );
};
