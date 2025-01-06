import React from 'react'

interface DatePickerProps {
  arrivalDate: string,
  departureDate: string,
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void, 
}

const DatePicker = ({arrivalDate, departureDate, onChange}: DatePickerProps) => {
  return (
    <div className='input-group horizontal'>
      <div className="row" style={{position: "relative"}}>
        <label htmlFor="arrivaleDate" >Arrive</label>
        <label 
          htmlFor="departureDate" 
          style={{
            position: "absolute",
            right: "160px"  
          }}>Departure</label>
      </div>
      <div className="row">
        <input 
          type="date" 
          name='arrivalDate' 
          value={arrivalDate}
          onChange={onChange}
          style={{
            width: "248px", 
            zIndex: 1000}}
          />
        <input
          type="date"
          name="departureDate" 
          value={departureDate}
          onChange={onChange}
          style={{
            width: "248px", 
            zIndex: 1000}}
          />
      </div>
   </div>
 )
}

export default DatePicker;