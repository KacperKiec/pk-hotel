import React, { useState } from 'react'

const HotelPanel = () => {
  const [hotelData, setHotelData] = useState({
    name: '',
    owner: '',
    country: '',
    city: '',
    address: '',
  });

  const handleChange = (e :React.ChangeEvent<HTMLInputElement>, field: keyof typeof hotelData) => {
    setHotelData({ ...hotelData, [field]: e.target.value});
  }

  return (
    <div className='add-hotel-container'>
      <h1>Add hotel</h1>
      <form className='add-hotel-form'>
        {Object.keys(hotelData).map((key) => {
          const field = key as keyof typeof hotelData;
          const label = field.charAt(0).toUpperCase() + field.slice(1); // Capitalize field name for the label
          return (
            <div key={field} className="add-hotel__field">
              <label className="add-hotel__label">{label}</label>
              <div>
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
        <button type="submit" className='save-new-hotel-btn'>Save</button>
      </form>
    </div>
  )
}


export default HotelPanel;