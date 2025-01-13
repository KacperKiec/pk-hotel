import React, { useState } from "react";
import "./Carousel.css";

interface CarouselProps {
  url: string[];
}

const Carousel = ({ url }: CarouselProps) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const nextSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % url.length);
  };

  const prevSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + url.length) % url.length);
  };

  const setSlide = (index: number) => {
    setCurrentIndex(index);
  };

  return (
    <div className="slideshow-container">
      {url.map((slide, index) => (
        <div
          key={index}
          className={`mySlides fade ${index === currentIndex ? "active" : ""}`}
          style={{ display: index === currentIndex ? "block" : "none" }}
        >
          <div className="numbertext">{`${index + 1} / ${url.length}`}</div>
          <img src={slide} style={{ width: "500px", height: "400px" }} />
        </div>
      ))}

      <a className="prev" onClick={prevSlide}>
        &#10094;
      </a>
      <a className="next" onClick={nextSlide}>
        &#10095;
      </a>

      <div style={{ textAlign: "center" }}>
        {url.map((_, index) => (
          <span
            key={index}
            className={`dot ${index === currentIndex ? "active-span" : ""}`}
            onClick={() => setSlide(index)}
          ></span>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
