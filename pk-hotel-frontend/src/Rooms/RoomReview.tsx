import React from "react";
import "./ReviewsPage.css";

interface ReviewProps {
  hotelName: string;
  userFirstName: string;
  userLastName: string;
  rating: number;
  comment: string;
}

const RoomReview = ({
  hotelName,
  userFirstName,
  userLastName,
  rating,
  comment,
}: ReviewProps) => {
  return (
    <div className="review-card">
      <h3>{hotelName}</h3>
      <p>
        Reviewed by: {userFirstName} {userLastName}
      </p>
      <p>Rating: {rating} / 5</p>
      {comment && <p>Comment: {comment}</p>}{" "}
      {/* Only display comment if it's not an empty string */}
    </div>
  );
};

export default RoomReview;
