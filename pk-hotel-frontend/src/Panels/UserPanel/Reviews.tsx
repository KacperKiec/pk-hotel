import React, { SetStateAction, useEffect, useState } from "react";
import "./Review.css";
import { User } from "../../Users/User";
import { AddReviewProps, addReviewApi } from "../../Api/Api";

interface ReviewsProps {
  hotelID: number;
  reviewAdded: boolean;
  user: User;
  setReviewAdded: React.Dispatch<SetStateAction<boolean>>;
}

const Reviews = ({
  hotelID,
  reviewAdded,
  setReviewAdded,
  user,
}: ReviewsProps) => {
  const [content, setContent] = useState("");
  const [rating, setRating] = useState<number | "">("");
  const [ratingError, setRatingError] = useState(false);
  const [addReviewError, setAddReviewError] = useState("");
  const maxCharCount = 150;

  useEffect(() => {
    setContent("");
    setRating("");
    setRatingError(false);
  }, [hotelID, reviewAdded]);

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (e.target.value.length <= maxCharCount) {
      setContent(e.target.value);
    }
  };

  const handleRatingChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setRating(value === "" ? "" : Math.min(5, Math.max(1, Number(value))));
    setRatingError(false); // Reset error when input changes
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (rating === "" || rating < 1 || rating > 5) {
      setRatingError(true);
      return;
    }
    const review: AddReviewProps = {
      user: { email: user.email },
      hotel: { id: hotelID },
      rating,
      content,
    };
    const response = addReviewApi(review);
    setReviewAdded(true);
  };

  return (
    <div className="review-container">
      {hotelID !== -1 && !reviewAdded && (
        <div className="review-container">
          <h1 className="review-h1">Add Review</h1>
          <form className="review-form" onSubmit={handleSubmit}>
            {/* Rating Input */}
            <label htmlFor="rating">Rating</label>
            <input
              id="rating"
              type="number"
              min="1"
              max="5"
              step="1"
              value={rating}
              onChange={handleRatingChange}
              placeholder="Enter a rating (1-5)"
              className={`rating-input ${ratingError ? "input-error" : ""}`}
            />
            {ratingError && (
              <div className="error-message">
                Rating is required and must be between 1 and 5.
              </div>
            )}
            <br />
            {/* Content Textarea */}
            <label htmlFor="content" className="review-label">
              Review
            </label>
            <textarea
              id="content"
              value={content}
              onChange={handleContentChange}
              placeholder="Write your review here..."
              rows={4}
              maxLength={maxCharCount}
              className="content-textarea"
            />
            <div className="char-counter">
              {content.length}/{maxCharCount} characters
            </div>

            {/* Submit Button */}
            <button type="submit" className="save-review-btn">
              Save
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Reviews;
