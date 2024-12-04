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
            <p>Standard: <span>{room.standard}</span></p>
            <p>Capacity: <span>{room.capacity}</span></p>
            <p className="desc">
              Description: <span>{room.description.length>150 ? room.description.substring(0, 140) + '...': room.description}</span>
              </p>
            <div className='price-tag'>
              <mark className='tertiary'>
                  Price
              </mark>
              <span className='price-value'>
                {room.price + " zł"}  
              </span> 
            </div>
          </section>
        </div>
      </div>
    </div>
  );
};


export default RoomCard;
