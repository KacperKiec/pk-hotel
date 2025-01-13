import React from "react";
import RoomReview from "./RoomReview";
import "./ReviewsPage.css";

// Define the types for the reviews data passed as props
export interface ReviewsPageProps {
  reviews: {
    hotelName: string;
    userFirstName: string;
    userLastName: string;
    rating: number;
    comment: string;
  }[];
}

const ReviewsPage = ({ reviews }: ReviewsPageProps) => {
  return (
    <div className="reviews-page">
      <h1>Reviews</h1>
      {reviews.map((review, index) => (
        <RoomReview
          key={index}
          hotelName={review.hotelName}
          userFirstName={review.userFirstName}
          userLastName={review.userLastName}
          rating={review.rating}
          comment={review.comment}
        />
      ))}
    </div>
  );
};

export default ReviewsPage;
