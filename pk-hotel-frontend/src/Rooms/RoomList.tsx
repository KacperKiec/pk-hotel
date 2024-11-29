import React, { useState } from 'react'
import { Room } from './Room';
import RoomCard from './RoomCard';
import './RoomCard.css'

interface RoomListProps {
  rooms: Room[],
}

const RoomList = ({rooms}: RoomListProps) => {
  console.log(rooms);

  const items = rooms.map(room => (
    <div key={room.roomNr} className='row'>
      <RoomCard room={room}/>
    </div>
  ));


  return (
    <div className="rooms-container">
      {items}
    </div>
  );
}


export default RoomList