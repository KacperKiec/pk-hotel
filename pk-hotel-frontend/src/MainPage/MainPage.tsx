import { useState } from "react";
import { Room } from "../Rooms/Room";
import RoomList from "../Rooms/RoomList";
import Filters from "../search/Filters";
import { SortBy } from "../search/SortBy";
import { SearchBar } from "../search/SearchBar";
import "./MainPage.css";

export const MainPage = () => {
  const [rooms, setRooms] = useState<Room[]>([]); // Initialize with an empty array

  const [selectedStandard, setSelectedStandard] = useState<number>(4);
  const [loading, setLoading] = useState(false);

  return (
    <div>
      <SearchBar
        standard={selectedStandard}
        setRooms={setRooms}
        setLoading={setLoading}
      />
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
              {loading && (
                <div className="center loading">
                  <span className="spinner primary loading-circle"></span>
                  <p>Loading...</p>
                </div>
              )}
              {!loading && <RoomList rooms={rooms} />}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
