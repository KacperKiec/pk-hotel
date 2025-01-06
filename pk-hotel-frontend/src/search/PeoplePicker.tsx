import React from 'react'

interface PeoplePickerProps {
  adultsValue: number,
  childrenValue: number,
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export const PeoplePicker = ({adultsValue, childrenValue, onChange}: PeoplePickerProps) => {
  return (
    <div className="input-group vertical">
      <div className="adults">
        <label 
            htmlFor="adults"
        >Adults: </label>
        <input 
            type="number" 
            name='adults'
            value={adultsValue}
            onChange={onChange}
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
              name='children'
              value={childrenValue}
              onChange={onChange}
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
