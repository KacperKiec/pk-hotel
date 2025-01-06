import React, { useState, useEffect, useRef } from "react";
import "./SearchBar.css";
import DatePicker from "./DatePicker";
import { PeoplePicker } from "./PeoplePicker";
import { getHotelsFromCity } from "../Api/Api";
import dayjs from "dayjs";
import { HotelDTO } from "../Hotel/Hotel";

interface SearchBarProps {
  standard: number;
}

export const SearchBar = ({ standard }: SearchBarProps) => {
  const [isDatePickerVisible, setDatePickerVisibility] = useState(false);
  const [isPeopleCountVisible, setPeopleCountVisibility] = useState(false);
  const [error, setError] = useState("");

  const [city, setCity] = useState("Miami");
  const [adults, setAdults] = useState(0);
  const [children, setChildren] = useState(0);

  const [arrivalDate, setArrivalDate] = useState(dayjs().format("YYYY-MM-DD"));
  const [departureDate, setDepartureDate] = useState(
    dayjs().format("YYYY-MM-DD")
  );

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
    const response = await getHotelsFromCity(city);
    if (response.message) {
      setError(response.message);
      return;
    }

    const hotelIds: number[] = [];

    response.data.forEach((element: any) => {
      hotelIds.push(element.id);
    });
-
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
