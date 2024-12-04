import React from 'react'

interface DatePickerProps {
  value: string,
  onChange: (e: any) => void, 
}

const DatePicker = ({value, onChange}: DatePickerProps) => {
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
          name='arrivaleDate' 
          value={value}
          onChange={onChange}
          style={{
            width: "248px", 
            zIndex: 1000}}
          />
        <input type="date" name="departureDate" style={{width: "248px", zIndex: 1000}}/>
      </div>
   </div>
 )
}

export default DatePicker;