import "./RoomPage.css";
import Carousel from "../Carousel/Carousel";
import { Room, transformRoom } from "./Room";
import { Link, useLocation } from "react-router-dom";
import dayjs from "dayjs";
import { Reservation } from "../Reservation/Reservation";
import { Extra } from "../Panels/AdminPanel/Extras/Extra";
import { useEffect, useState } from "react";
import { Convenience } from "../Panels/AdminPanel/Room/AddRoom";
import {
  findHotelByName,
  getAllExtrasApi,
  getAllReviewsApi,
  makeReservationApi,
} from "../Api/Api";
import { User, transformUser } from "../Users/User";
import ReviewsPage, { ReviewsPageProps } from "./ReviewsPage";

const RoomPage = () => {
  const [selectedExtras, setSelectedExtras] = useState<Extra[]>([]);
  const [error, setError] = useState("");
  const location = useLocation();

  const [allExtras, setAllExtras] = useState<Extra[]>([]);

  const [showReviews, setShowReviews] = useState(false);
  const [reviews, setReviews] = useState<ReviewsPageProps["reviews"]>([]);

  const getExtras = async () => {
    const response = await getAllExtrasApi();
    if (response.status === 200) return response.data as Extra[];
    return [];
  };

  useEffect(() => {
    const fetchExtras = async () => {
      const extrasData = await getExtras();
      setAllExtras(extrasData); // Update state
    };

    fetchExtras(); // Call the async function inside useEffect
  }, []);

  // Add a safe check for location.state
  const data = location.state?.room; // room will be undefined if location.state is null
  let startDate = location.state?.startDate;
  let endDate = location.state?.endDate;

  const conveniences: string = "";

  const room = new Room({
    hotelName: data._hotelName,
    roomNr: data._roomNr,
    standard: data._standard,
    places: data._places,
    description: data._description,
    price: data._price,
    imagesUrl: data._imagesUrl,
    conveniences: [],
    reviews: data._reviews,
    hotelId: data.hotelId,
  });
  data._conveniences.forEach((element: Convenience) => {
    room.conveniences.push(element.name);
  });

  const getHotelId = async () => {
    const response = await findHotelByName(room.hotelName);
    if (response.message) {
      setError(response.message);
      return;
    }
    if (response.data) room.hotelId = response.data[0].id;
  };
  getHotelId();

  const loggedUser = location.state?.loggedUser as User | undefined;

  const reservation: Reservation = new Reservation({
    room: room,
    extras: selectedExtras,
    startDate,
    endDate,
  });

  if (!room) {
    return <div>Room data not found</div>; // Handle the case where room data is missing
  }

  if (!startDate || !endDate) {
    startDate = "N/A";
    endDate = "N/A";
  }
  const handleCheckboxChange = (e: any, element: Extra) => {
    if (e.target.checked) {
      // Add the extra to the selectedExtras if checked
      setSelectedExtras((prevState) => [...prevState, element]);
    } else {
      // Remove the extra from the selectedExtras if unchecked
      setSelectedExtras((prevState) =>
        prevState.filter((extra) => extra.name !== element.name)
      );
    }
  };

  const onClick = async () => {
    if (loggedUser === undefined) {
      window.location.href = "/login";
      return;
    }

    const response = await makeReservationApi({
      user: transformUser(loggedUser),
      room: transformRoom(room),
      startDate,
      endDate,
      extras: selectedExtras,
    });
    if (response.message) {
      setError(response.message); // Set the error message
    } else {
      setError(""); // Clear any previous errors if the reservation is successful
      window.location.href = "/payments"; // Redirect manually if no error
    }
  };

  const getReviews = async () => {
    if (showReviews) {
      setShowReviews(false);
      return;
    }

    setError("");
    const response = await getAllReviewsApi(room.hotelId);
    if (response.message) {
      setError(response.message);
      setShowReviews(false);
      return;
    }
    const reviewsData =
      response.data?.map((review) => ({
        hotelName: review.hotelName || "",
        userFirstName: review.userFirstName || "",
        userLastName: review.userLastName || "",
        rating: review.rating || 0,
        comment: review.comment || "",
      })) || [];

    setShowReviews(true);
    setReviews(reviewsData); // Reviews will always be an array
  };

  return (
    <div className="content-container">
      <div className="h1-container">
        <h1>{room.hotelName}</h1>
      </div>
      <Carousel url={room.imagesUrl} />
      <p>
        Standard: <span>{room.standard}</span>
      </p>
      <p>
        Capacity: <span>{room.places}</span>
      </p>
      <p>
        Date:{" "}
        <span>
          {startDate.replace("-", ".")} - {endDate.replace("-", ".")} (
          {dayjs(endDate).diff(dayjs(startDate), "day")} days)
        </span>
      </p>
      <p>
        Description: <span>{room.description}</span>
      </p>
      <p>
        Rating:{" "}
        <span>
          {room.reviews}/5 (
          <span className="see-reviews" onClick={getReviews}>
            see reviews
          </span>
          )
        </span>
      </p>
      <p style={{ marginTop: "20px" }}>Conveniences:</p>
      <div className="conveniences-list">
        {room.conveniences.map((element, index) => (
          <span key={index} className="convenience-span">
            {element}
          </span>
        ))}
      </div>

      {allExtras.length > 0 && (
        <>
          <p>Extras (Price per day):</p>
          {allExtras.map((element, index) => (
            <div key={index} className="extras">
              <input
                type="checkbox"
                id={`extra-${index}`}
                checked={selectedExtras.some(
                  (extra) => extra.name === element.name
                )}
                onChange={(e) => handleCheckboxChange(e, element)}
              />
              <label
                htmlFor={`extra-${index}`}
                style={{ textAlign: "center", margin: 0 }}
              >
                {element.name} - {element.pricePerDay} zł
              </label>
            </div>
          ))}
        </>
      )}

      <p className="price">
        Price: <span>{reservation.calculatePrice()}zł</span>
      </p>
      <div className="btn-container">
        <button className="reserve-btn" onClick={onClick}>
          Reserve
        </button>
      </div>
      {error && <p className="error-message">{error}</p>}
      {showReviews && reviews.length > 0 && <ReviewsPage reviews={reviews} />}
      {showReviews && reviews.length === 0 && <p>No reviews available.</p>}
    </div>
  );
};

export default RoomPage;
