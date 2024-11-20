CREATE TABLE hotel (
   id int(8) PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   owner VARCHAR(100) NOT NULL,
   register_date DATE NOT NULL,
   country VARCHAR(30) NOT NULL,
   city VARCHAR(30) NOT NULL,
   address VARCHAR(100) NOT NULL
);

CREATE TABLE room (
   hotel_id int(8),
   room_nr int(8),
   places int(3) NOT NULL,
   price DECIMAL(10, 2) NOT NULL,
   standard enum('LOW', 'AVERAGE', 'HIGH') NOT NULL,
   description TEXT,
   PRIMARY KEY (hotel_id, room_nr),
   FOREIGN KEY (hotel_id) REFERENCES hotel(id)
);

CREATE TABLE client (
    id int(8) PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    birth_date DATE
);

CREATE TABLE review (
    id int(8) PRIMARY KEY AUTO_INCREMENT,
    hotel_id int(8),
    client_id int(8),
    rating int(3),
    content TEXT,
    FOREIGN KEY (hotel_id) REFERENCES hotel(id),
    FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE convenience (
   id int(8) PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(70) NOT NULL
);

CREATE TABLE room_convenience (
    room_nr int(8),
    hotel_id int(8),
    convenience_id int(8),
    PRIMARY KEY (hotel_id, room_nr, convenience_id),
    FOREIGN KEY (hotel_id, room_nr) REFERENCES room(hotel_id, room_nr),
    FOREIGN KEY (convenience_id) REFERENCES convenience(id)
);

CREATE TABLE reservation (
    id int(8) PRIMARY KEY AUTO_INCREMENT,
    client_id int(8),
    hotel_id int(8),
    room_nr int(8),
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status enum('DELETED', 'ACCEPTED', 'IN_REALISATION', 'ENDED'),
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (hotel_id, room_nr) REFERENCES room(hotel_id, room_nr)
);

CREATE TABLE extra (
   id int(8) PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(70) NOT NULL,
   daily_price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE reservation_extra (
    reservation_id int(8),
    extra_id int(8),
    PRIMARY KEY (reservation_id, extra_id),
    FOREIGN KEY (reservation_id) REFERENCES reservation(id),
    FOREIGN KEY (extra_id) REFERENCES extra(id)
);