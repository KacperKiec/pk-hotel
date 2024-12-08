CREATE TABLE image(
    id int(8) PRIMARY KEY AUTO_INCREMENT,
    path varchar(100) NOT NULL
);

CREATE TABLE room_image(
    image_id int(8),
    room_nr int(8),
    hotel_id int(8),
    PRIMARY KEY (image_id, hotel_id, room_nr),
    FOREIGN KEY (hotel_id, room_nr) REFERENCES room(hotel_id, room_nr),
    FOREIGN KEY (image_id) REFERENCES image(id)
);