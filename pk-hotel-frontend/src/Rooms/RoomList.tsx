import React, { useState } from "react";
import { Room } from "./Room";
import RoomCard from "./RoomCard";
import "./RoomCard.css";
import { start } from "repl";
import { User } from "../Users/User";

interface RoomListProps {
  rooms: Room[];
  loggedUser: User | undefined;
  startDate: string;
  endDate: string;
}

const RoomList = ({ rooms, startDate, endDate, loggedUser }: RoomListProps) => {
  const items = rooms.map((room, index) => (
    <div key={index} className="row">
      <RoomCard
        room={room}
        startDate={startDate}
        endDate={endDate}
        loggedUser={loggedUser}
      />
    </div>
  ));

  return <div className="rooms-container">{items}</div>;
};

export default RoomList;
