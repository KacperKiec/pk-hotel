import { useEffect, useState } from "react";
import { Room } from "../Rooms/Room";
import RoomList from "../Rooms/RoomList";
import Filters from "../search/Filters";
import { SortBy } from "../search/SortBy";
import { SearchBar, getImagesUrl } from "../search/SearchBar";
import "./MainPage.css";
import dayjs from "dayjs";
import { getRoomsWithFilters } from "../Api/Api";
import { User } from "../Users/User";

interface MainPageProps {
  loggedUser: User | undefined;
}

export const MainPage = ({ loggedUser }: MainPageProps) => {
  const [rooms, setRooms] = useState<Room[]>([]); // Initialize with an empty array
  const [selectedStandard, setSelectedStandard] = useState<number>(4);
  const [loading, setLoading] = useState(false);
  const [startDate, setStartDate] = useState(dayjs().format("YYYY-MM-DD"));
  const [endDate, setEndDate] = useState(
    dayjs().add(1, "day").format("YYYY-MM-DD")
  );
  const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc"); // Sorting order state

  useEffect(() => {
    setLoading(true);
    const fetchRooms = async () => {
      const response = await getRoomsWithFilters({
        startDate,
        endDate,
        page: 0,
      });
      if (response.data) {
        setRooms([]);
        response.data.forEach((element) => {
          setRooms((prev) => [
            ...prev,
            new Room({
              hotelName: element.hotelName,
              roomNr: element.roomNr,
              standard: element.standard,
              places: element.places,
              description: element.description,
              price: element.price,
              imagesUrl: getImagesUrl(element.images),
              reviews: element.rating,
              conveniences: element.conveniences,
              name: element.name,
            }),
          ]);
        });
      }
    };

    fetchRooms();
    setTimeout(() => {
      setLoading(false);
    }, 600);
  }, [startDate, endDate]); // Adding startDate and endDate to the dependency array

  // Sorting function to sort rooms by price
  const sortRooms = (rooms: Room[], order: "asc" | "desc") => {
    return [...rooms].sort((a, b) =>
      order === "asc" ? a.price - b.price : b.price - a.price
    );
  };

  // Handle sorting order change
  const handleSortChange = () => {
    setSortOrder((prev) => (prev === "asc" ? "desc" : "asc"));
  };

  const sortedRooms = sortRooms(rooms, sortOrder);

  return (
    <div>
      <SearchBar
        standard={selectedStandard}
        setRooms={setRooms}
        setLoading={setLoading}
        arrivalDate={startDate}
        departureDate={endDate}
        setArrivalDate={setStartDate}
        setDepartureDate={setEndDate}
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
              <SortBy onSortChange={handleSortChange} sortOrder={sortOrder} />
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
              {!loading && (
                <RoomList
                  rooms={sortedRooms} // Display sorted rooms
                  startDate={startDate}
                  endDate={endDate}
                  loggedUser={loggedUser}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
