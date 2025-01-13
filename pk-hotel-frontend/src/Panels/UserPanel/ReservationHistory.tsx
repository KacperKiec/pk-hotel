import React, { useEffect, useState } from "react";
import { Reservation } from "../../Reservation/Reservation";
import { findHotelByName, getAllUserReservationsApi } from "../../Api/Api";
import { Room } from "../../Rooms/Room";
import Reviews from "./Reviews";
import { User } from "../../Users/User";

interface ReservationHistoryProps {
  loggedUser: User;
}

const ReservationHistory = ({ loggedUser }: ReservationHistoryProps) => {
  const [reservations, setReservations] = useState<Reservation[]>([]);
  const [error, setError] = useState("");
  const [clickedHotel, setClickedHotel] = useState(-1);
  const [reviewAdded, setReviewAdded] = useState(false);

  const getReservations = async () => {
    const response = await getAllUserReservationsApi();
    if (response.data) {
      setReservations(
        response.data.map(
          (element: any) =>
            new Reservation({
              room: new Room({
                roomNr: element.roomNr,
                hotelName: element.hotelName,
              }),
              startDate: element.checkInDate,
              endDate: element.checkOutDate,
            })
        )
      );
    }
  };

  useEffect(() => {
    getReservations();
  }, []);

  const onRowClick = async (name: string) => {
    setClickedHotel(-1);
    const response = await findHotelByName(name);
    let id = -1;
    if (response.data) id = response.data[0].id;
    setClickedHotel(id);
    setReviewAdded(false);
  };

  return (
    <div className="reviews-container">
      <h1 className="reviews-h1" style={{ marginLeft: "20px" }}>
        Reservation history
      </h1>
      <table className="extras-table hoverable">
        <thead>
          <tr>
            <th>Hotel name</th>
            <th>Room nr</th>
            <th>Check in date</th>
            <th>Check out date</th>
          </tr>
        </thead>
        <tbody>
          {reservations.map((element, index) => (
            <tr
              key={index}
              onClick={() => onRowClick(element.room.hotelName)}
              title="Add review"
            >
              <td data-label="hotel-name">{element.room.hotelName}</td>
              <td data-label="room-nr">{element.room.roomNr}</td>
              <td data-label="check-in-date">{element.startDate}</td>
              <td data-label="check-out-date">{element.endDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
      {error && <div className="admin-panel-error">{error}</div>}
      <Reviews
        hotelID={clickedHotel}
        setReviewAdded={setReviewAdded}
        reviewAdded={reviewAdded}
        user={loggedUser}
      />
    </div>
  );
};

export default ReservationHistory;
