import React, { useState } from 'react'
import './SearchBar.css'
import DatePicker from './DatePicker';
import { PeoplePicker } from './PeoplePicker';

export const SearchBar = () => {
  const[isDatePickerVisible, setDatePickerVisibility] = useState(false);
  const[isPeopleCountVisible, setPeopleCountVisibility] = useState(false);

  const handleDateClick = (e: any) => {
    e.preventDefault();
    setPeopleCountVisibility(false);
    setDatePickerVisibility(!isDatePickerVisible);
  }

  const handlePeopleClick = (e: any) => {
    e.preventDefault();
    setDatePickerVisibility(false);
    setPeopleCountVisibility(!isPeopleCountVisible);
  }

  return (
    <div className="form-container">
      <form action="input-group horizontal">
        <div className='container'>
          <div className="row">
            <input 
              type="text" 
              id="place" 
              placeholder='Gdzie jedziesz?' 
              className='col-sm-6 place'
            />
            <div className="button-group horizontal col-sm">
              <button onClick={handleDateClick}>
                <span className='icon-calendar icons'></span>
              </button>
              <button onClick={handlePeopleClick}>
                <span className='icon-user icons'></span>
              </button>
            </div>
              <button className='search-button'>
                <span className='icon-search inverse icons'></span>
              </button>
          </div>
        </div>
        {isDatePickerVisible && (
        <div className='date'>
          <DatePicker/>   
        </div>
        )}   
        {isPeopleCountVisible &&(
          <div className='people'>
            <PeoplePicker/>
          </div>
        )}
      </form>
    </div>
  )
}
