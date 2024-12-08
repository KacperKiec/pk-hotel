import React, { useState } from 'react'
import './AdminPanel.css'
import { addHotelApi, Response } from '../../Api/Api';
import { Hotel, HotelDTO, transfromHotel } from '../../Hotel/Hotel';

const AddHotel = () => {
  const [hotelData, setHotelData] = useState({
    name: '',
    owner: '',
    country: '',
    city: '',
    address: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, field: keyof typeof hotelData) => {
    setHotelData({ ...hotelData, [field]: e.target.value});
  }

  const [error, setError] = useState('');
  const [confirmMessage, setConfirmMessage] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    Object.keys(hotelData).forEach((key) => {
      const field = key as keyof typeof hotelData;
      if(hotelData[field].length === 0){
        setError(`${field.at(0)?.toUpperCase} field have to be filled.`);
      }
    })
    if(error.length>0){
      return;
    }

    const hotel: Hotel = new Hotel(hotelData);
    const hotelDTO: HotelDTO = transfromHotel(hotel);
    console.log(hotelDTO);
    const response: Response = await addHotelApi(transfromHotel(hotel)); 
    if(response.status === 202){
      setConfirmMessage('Hotel was added.');
      setHotelData({
        name: '',
        owner: '',
        country: '',
        city: '',
        address: '',
      })
    }
    else if(response.status === 404){
      setConfirmMessage('There was an error while connecting to the server.');
    }
    else{
      setConfirmMessage('There was an error while connecting to the server.');
    }
    console.log(response.status);
  }

  return (
    <div className='add-hotel-container'>
      <h1 className='add-hotel-h1'>Add hotel</h1>
      <form className='add-hotel-form' onSubmit={handleSubmit}>
        {Object.keys(hotelData).map((key) => {
          const field = key as keyof typeof hotelData;
          const label = field.charAt(0).toUpperCase() + field.slice(1); // Capitalize field name for the label
          return (
            <div key={field} className="add-hotel__field">
              <label className="add-hotel__label">{label}</label>
              <div className='add-hotel-inputs'>
                <input
                  name={field}
                  type="text"
                  value={hotelData[field]}
                  onChange={(e) => handleChange(e, field)}
                  className="add-hotel__input"
                />
              </div>
            </div>
          )}
        )}
        <div className='submit-errors'>
          <span>
            {error.length>0 && 
              <div className='add-hotel-error'>
                {error}
              </div>
            }
            {confirmMessage === 'Hotel was added.' ? 
              <div className='positive-response'>
                {confirmMessage}
              </div>
              :
              <div className='add-hotel-error'>
              {confirmMessage}
              </div>
            }
          </span>
          <button type="submit" className='save-new-hotel-btn'>Save</button>
        </div>
      </form>
    </div>
  )
}


export default AddHotel;