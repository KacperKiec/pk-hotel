import React, { useState } from "react";
import { RoomImage } from "../RoomImage";
import "./Slider.css";

interface SliderProps {
  imagesUrl: string[];
  setRoomData: React.Dispatch<
    React.SetStateAction<{
      hotelId: string;
      roomNr: string;
      standard: string;
      places: string;
      price: string;
      description: string;
      imagesUrl: string[];
      conveniences: string[];
    }>
  >;
}

const Slider = ({ imagesUrl, setRoomData }: SliderProps) => {
  const [currentSlide, setCurrentSlide] = useState(0);

  const totalSlides = imagesUrl.length + 1;
  const slidesToShow = 3;

  const handleNext = () => {
    if (currentSlide < totalSlides - slidesToShow) {
      setCurrentSlide(currentSlide + 1);
    }
  };

  const handlePrev = () => {
    if (currentSlide > 0) {
      setCurrentSlide(currentSlide - 1);
    }
  };

  return (
    <div className="images-conteiner">
      <button className="prev-btn" type="button" onClick={handlePrev}>
        <img src={`/assets/right-arrow.png`} alt="Previous" />
      </button>
      <div
        className="slider-track"
        style={{
          transform: `translateX(-${currentSlide * (100 / slidesToShow)}%)`,
        }}
      >
        {imagesUrl.map((element, index) => (
          <RoomImage key={index} imageUrl={element} setRoomData={setRoomData} />
        ))}
      </div>
      <button className="next-btn" type="button" onClick={handleNext}>
        <img src={`/assets/right-arrow.png`} alt="Next" />
      </button>
    </div>
  );
};

export default Slider;
