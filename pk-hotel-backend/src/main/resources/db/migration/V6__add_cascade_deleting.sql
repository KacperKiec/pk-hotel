ALTER TABLE room
DROP FOREIGN KEY room_ibfk_1;
ALTER TABLE room
    ADD CONSTRAINT room_ibfk_1
        FOREIGN KEY (hotel_id)
            REFERENCES hotel(id)
            ON DELETE CASCADE;

ALTER TABLE reservation
DROP FOREIGN KEY reservation_ibfk_2;
ALTER TABLE reservation
    ADD CONSTRAINT reservation_ibfk_2
        FOREIGN KEY (hotel_id, room_nr)
            REFERENCES room(hotel_id, room_nr)
            ON DELETE CASCADE;


ALTER TABLE room_convenience
DROP FOREIGN KEY room_convenience_ibfk_1;
ALTER TABLE room_convenience
    ADD CONSTRAINT room_convenience_ibfk_1
        FOREIGN KEY (hotel_id, room_nr)
            REFERENCES room(hotel_id, room_nr)
            ON DELETE CASCADE;
