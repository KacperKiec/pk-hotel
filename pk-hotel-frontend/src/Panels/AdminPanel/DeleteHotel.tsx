import React, { useState } from 'react'
import './AdminPanel.css'
import { removeHotelApi, Response } from '../../Api/Api';

export const DeleteHotel = () => {
  const [hotelId, setHotelId] = useState('');
  const [message, setMessage] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setHotelId(e.target.value);
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if(hotelId === ''){
      setMessage('Id must be filled.');
      return;
    }
    const response: Response = await removeHotelApi(Number(hotelId));
    if(response.status === 204){
      setMessage('Hotel was removed.');
    }
    else if(response.status === 404){
      setMessage('There is no hotel with this ID.')
    }
    else{
      setMessage('Unable to connect to the server.');
    }
  }

  return (
    <div className='delete-hotel-container'>
    <h1 className='delete-hotel-h1'>Delete hotel</h1>
    <form className='delete-hotel-form' onSubmit={handleSubmit}>
      <label className="delete-hotel-label">ID</label>
      <div>
        <input
          name='id'
          type="text"
          value={hotelId}
          onChange={handleChange}
          className="delete-hotel-input"
        />
      </div>
      <div className='submit-errors'>
        <span>
          <div className={`delete-hotel ${message.includes('Hotel was removed.') ? 'positive-message': ''}`}>{message}</div>
        </span>
        <button type="submit" className='delete-hotel-btn'>Save</button>
      </div>
    </form>
  </div>
  )
}
