import React, { useEffect, useState } from "react";
import { Room } from "./Room";
import "./RoomCard.css";
import { Link } from "react-router-dom";
import { start } from "repl";
import { User } from "../Users/User";

interface RoomCardProps {
  room: Room;
  loggedUser: User | undefined;
  startDate: string;
  endDate: string;
}

const RoomCard = ({ room, startDate, endDate, loggedUser }: RoomCardProps) => {
  return (
    <div className="col-sm-12">
      <Link to="/room-page" state={{ room, startDate, endDate, loggedUser }}>
        <div className="card fluid room-card">
          <div className="row card-content">
            <img
              className="room-image"
              src={room.imagesUrl[0]}
              alt="room image"
            />
            <section className="section dark room-info">
              <h3 className="strong">{room.hotelName}</h3>
              <p>
                Standard: <span>{room.standard}</span>
              </p>
              <p>
                Capacity: <span>{room.places}</span>
              </p>
              <p className="desc">
                Description:{" "}
                <span>
                  {room.description.length > 150
                    ? room.description.substring(0, 140) + "..."
                    : room.description}
                </span>
              </p>
              <div className="price-tag">
                <mark className="tertiary">Price</mark>
                <span className="price-value">{room.price + " zł"}</span>
              </div>
            </section>
          </div>
        </div>
      </Link>
    </div>
  );
};

export default RoomCard;
