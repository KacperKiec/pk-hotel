import { useState } from "react";
import { MOCK_ROOMS } from "../Rooms/MockRooms";
import { Room } from "../Rooms/Room";
import RoomList from "../Rooms/RoomList";
import Filters from "../search/Filters";
import { SortBy } from "../search/SortBy";
import { SearchBar } from "../search/SearchBar";

export const MainPage = () => {
  const [rooms, setRooms] = useState<Room[]>(MOCK_ROOMS);
  const [selectedStandard, setSelectedStandard] = useState<number>(1);

  return (
    <div>
      <SearchBar standard={selectedStandard} />
      <div className="container">
        <div
          className="container-filters-rooms"
          style={{
            width: "1200px",
            justifySelf: "center",
          }}
        >
          <div className="row">
            <div className="col-sm-2"></div>
            <div className="col sm">
              <SortBy />
            </div>
          </div>
          <div className="row">
            <div className="col-sm-2">
              <Filters
                selected={selectedStandard}
                setSelected={setSelectedStandard}
              />
            </div>
            <div className="col-sm">
              <RoomList rooms={rooms} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
