import React from 'react'

export const PeoplePicker = () => {
  return (
    <div className='row'>
        <div className="col-sm-6"></div>
        <div className="col-sm">
            <label 
                htmlFor="adults"
                style={{marginLeft: "12px"}}
            >Adults: 1</label>
            <input 
                type="number" 
                style={{
                    width: "60px",
                    fontSize: "12px",
                    marginRight: "115px",
                    height: "26px",
            }}/>
            <label htmlFor="children">Children: 1</label>
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
