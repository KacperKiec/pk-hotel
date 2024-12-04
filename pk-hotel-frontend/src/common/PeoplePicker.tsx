import React from 'react'

export const PeoplePicker = () => {
  return (
    <div className="input-group vertical">
      <div className="adults">
        <label 
            htmlFor="adults"
        >Adults: </label>
        <input 
            type="number" 
            style={{
                width: "60px",
                fontSize: "12px",
                height: "26px",
                marginLeft: "18px"
        }}/>
      </div>
      <div className="children">
        <label htmlFor="children">Children: </label>
          <input 
              type="number" 
              style={{
                  width: "60px",
                  fontSize: "12px",
                  marginRight: "20px",
                  height: "26px"
          }}/> 
        </div>
    </div>
  )
}
