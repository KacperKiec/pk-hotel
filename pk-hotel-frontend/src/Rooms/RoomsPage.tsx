import React, { useState } from 'react'
import { MOCK_ROOMS } from './MockRooms'
import { Room } from './Room'
import RoomList from './RoomList';
import Filters from '../common/Filters';
import { SortBy } from '../common/SortBy';
import { SearchBar } from '../common/SearchBar';

export const RoomsPage = () => {
  const [rooms, setRooms] = useState<Room[]>(MOCK_ROOMS);

  return (
    <div>
      <SearchBar/>
      <div className='container'>
        <div 
          className='container-filters-rooms'
          style={{
            width: "1200px",
            justifySelf: "center"
          }}
        >
          <div className="row">
            <div className="col-sm-2"></div>
            <div className="col sm">
              <SortBy/>
            </div>
          </div>
          <div className="row">
            <div className="col-sm-2">
              <Filters/>
            </div>
            <div className='col-sm'>
              <RoomList rooms={rooms}/> 
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
