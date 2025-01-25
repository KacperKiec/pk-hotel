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

ALTER TABLE reservation
DROP FOREIGN KEY reservation_ibfk_1;
ALTER TABLE reservation
    ADD CONSTRAINT reservation_ibfk_1
        FOREIGN KEY (client_id)
            REFERENCES user(id)
            ON DELETE CASCADE;

ALTER TABLE reservation_extra
DROP FOREIGN KEY reservation_extra_ibfk_2;
ALTER TABLE reservation_extra
    ADD CONSTRAINT reservation_extra_ibfk_2
        FOREIGN KEY (extra_id)
            REFERENCES extra(id)
            ON DELETE CASCADE;


ALTER TABLE room_convenience
DROP FOREIGN KEY room_convenience_ibfk_1;
ALTER TABLE room_convenience
    ADD CONSTRAINT room_convenience_ibfk_1
        FOREIGN KEY (hotel_id, room_nr)
            REFERENCES room(hotel_id, room_nr)
            ON DELETE CASCADE;

ALTER TABLE room_image
DROP FOREIGN KEY room_image_ibfk_2;
ALTER TABLE room_image
    ADD CONSTRAINT room_image_ibfk_2
        FOREIGN KEY (image_id)
            REFERENCES image(id)
            ON DELETE CASCADE;

ALTER TABLE room_image
DROP FOREIGN KEY room_image_ibfk_1;
ALTER TABLE room_image
    ADD CONSTRAINT room_image_ibfk_1
        FOREIGN KEY (hotel_id, room_nr)
            REFERENCES room(hotel_id, room_nr)
            ON DELETE CASCADE;

ALTER TABLE review
DROP FOREIGN KEY review_ibfk_1;
ALTER TABLE review
    ADD CONSTRAINT review_ibfk_1
        FOREIGN KEY (hotel_id) REFERENCES hotel(id)
            ON DELETE CASCADE;

ALTER TABLE review
DROP FOREIGN KEY review_ibfk_2;
ALTER TABLE review
    ADD CONSTRAINT review_ibfk_2
        FOREIGN KEY (client_id) REFERENCES user(id)
            ON DELETE SET NULL;