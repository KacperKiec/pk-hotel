import React from 'react'
import { Room } from './Room';
import './RoomCard.css'

interface RoomCardProps {
  room: Room;
}

const RoomCard = ({ room }: RoomCardProps) => {
  return (
    <div className="col-sm-12">
      <div className="card fluid">
        <div className="row card-content">
          <img className="room-image" src={room.imageUrl} alt="room image" />
          <section className="section dark room-info">
            <h5 className="strong">
              <strong>{room.name}</strong>
            </h5>
            <p>Standard: {room.standard}</p>
            <p>Capacity: {room.capacity}</p>
            <p className="desc">Description: {room.description}</p>
            <p>Price: {room.price}</p>
          </section>
        </div>
      </div>
    </div>
  );
};


export default RoomCard;
