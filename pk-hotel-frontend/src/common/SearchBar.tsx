import React, { useState, useEffect, useRef } from "react";
import "./SearchBar.css";
import DatePicker from "./DatePicker";
import { PeoplePicker } from "./PeoplePicker";
import dayjs from "dayjs";

export const SearchBar = () => {
  const [isDatePickerVisible, setDatePickerVisibility] = useState(false);
  const [isPeopleCountVisible, setPeopleCountVisibility] = useState(false);
  
  const [adults, setAdults] = useState(0);
  const [children, setChildren] = useState(0);

  const [arrivalDate, setArrivalDate] = useState(dayjs().format('YYYY-MM-DD'));
  const [departureDate, setDepatureDate] = useState(dayjs().format('YYYY-MM-DD'));

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

  return (
    <div className="form-container">
      <form action="input-group horizontal">
        <div className="container">
          <div className="row">
            <input
              type="text"
              id="place"
              placeholder="Where are you going?"
              className="col-sm-6 place"
            />
            <div className="button-group horizontal col-sm">
              <button onClick={handleDateClick} ref={dateButtonRef}>
                <span className="icon-calendar icons"></span>
              </button>
              <button onClick={handlePeopleClick} ref={peopleButtonRef}>
                <span className="icon-user icons"></span>
              </button>
            </div>
            <button className="search-button">
              <span className="icon-search inverse icons"></span>
            </button>
          </div>
        </div>
        {isDatePickerVisible && (
          <div className="date" ref={datePickerRef}>
            <DatePicker />
          </div>
        )}
        {isPeopleCountVisible && (
          <div className="date people" ref={peoplePickerRef}>
            <PeoplePicker />
          </div>
        )}
      </form>
    </div>
  );
};
