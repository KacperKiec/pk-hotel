import React, { useState } from 'react'

const Filters = () => {
  const[selected, setSelected] = useState(1);

  const handleChange = (index: number) => {
    if(index !== selected){
      setSelected(index);
    }
  };

  return (
    <div className='filters-container'>
      <span>
        <strong>Filter by:</strong>
      </span>
      <hr />
      <div className="input-group vertical">
        <div className="row">
          <input 
            type="checkbox" 
            name="isApartament"
            checked={selected === 1}
            onChange={() => handleChange(1)}
            />
          <label htmlFor="isApartament">Apartament</label>
        </div>
        <div className="row">
          <input 
            type="checkbox" 
            name="isHotel"
            checked={selected === 2}
            onChange={() => handleChange(2)}
            />
          <label htmlFor="isHotel">Hotel</label>
        </div>
        <div className="row">
          <input 
            type="checkbox" 
            name="isHostel"
            checked={selected === 3}
            onChange={() => handleChange(3)}
            />
          <label htmlFor="isHostel">Hostel</label>
        </div>
        <div className="row">
          <input 
            type="checkbox" 
            name="isHomeStay"
            checked={selected === 4}
            onChange={() => handleChange(4)}
            />
          <label htmlFor="isHomestay">Homestay</label>
        </div>
      </div>
    </div>
  )
}

export default Filters;