-- Assign specific conveniences and images to each room

-- Rooms for 'Hotel Blue Sky' (ID 1)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (1, 1, 1), -- Free WiFi
    (1, 2, 2), -- Breakfast Included
    (1, 3, 3), -- Swimming Pool
    (1, 4, 4); -- Gym

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (1, 1, 1), -- Image 1
    (1, 2, 2), -- Image 2
    (1, 3, 3), -- Image 3
    (1, 4, 4); -- Image 4

-- Rooms for 'Hotel Sunshine' (ID 2)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (2, 1, 5), -- Parking
    (2, 2, 1), -- Free WiFi
    (2, 3, 2), -- Breakfast Included
    (2, 4, 3); -- Swimming Pool

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (2, 1, 5), -- Image 5
    (2, 2, 6), -- Image 6
    (2, 3, 7), -- Image 7
    (2, 4, 8); -- Image 8

-- Rooms for 'Grand Palace Hotel' (ID 3)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (3, 1, 4), -- Gym
    (3, 2, 1), -- Free WiFi
    (3, 3, 5), -- Parking
    (3, 4, 2); -- Breakfast Included

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (3, 1, 9),  -- Image 9
    (3, 2, 10), -- Image 10
    (3, 3, 11), -- Image 11
    (3, 4, 12); -- Image 12

-- Rooms for 'Mountain View Resort' (ID 4)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (4, 1, 3), -- Swimming Pool
    (4, 2, 5), -- Parking
    (4, 3, 4), -- Gym
    (4, 4, 1); -- Free WiFi

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (4, 1, 13), -- Image 13
    (4, 2, 14), -- Image 14
    (4, 3, 15), -- Image 15
    (4, 4, 16); -- Image 16

-- Rooms for 'Ocean Breeze Hotel' (ID 5)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (5, 1, 2), -- Breakfast Included
    (5, 2, 3), -- Swimming Pool
    (5, 3, 4), -- Gym
    (5, 4, 5); -- Parking

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (5, 1, 17), -- Image 17
    (5, 2, 18), -- Image 18
    (5, 3, 19), -- Image 19
    (5, 4, 20); -- Image 20

-- Rooms for 'Lake Side Hotel' (ID 6)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (6, 1, 5), -- Parking
    (6, 2, 2), -- Breakfast Included
    (6, 3, 1), -- Free WiFi
    (6, 4, 3); -- Swimming Pool

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (6, 1, 1), -- Image 1
    (6, 2, 2), -- Image 2
    (6, 3, 3), -- Image 3
    (6, 4, 4); -- Image 4

-- Rooms for 'Desert Mirage Resort' (ID 7)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (7, 1, 1), -- Free WiFi
    (7, 2, 4), -- Gym
    (7, 3, 3), -- Swimming Pool
    (7, 4, 5); -- Parking

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (7, 1, 5), -- Image 5
    (7, 2, 6), -- Image 6
    (7, 3, 7), -- Image 7
    (7, 4, 8); -- Image 8

-- Rooms for 'City Lights Hotel' (ID 8)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (8, 1, 2), -- Breakfast Included
    (8, 2, 3), -- Swimming Pool
    (8, 3, 4), -- Gym
    (8, 4, 5); -- Parking

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (8, 1, 9),  -- Image 9
    (8, 2, 10), -- Image 10
    (8, 3, 11), -- Image 11
    (8, 4, 12); -- Image 12

-- Rooms for 'Green Fields Hotel' (ID 9)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (9, 1, 3), -- Swimming Pool
    (9, 2, 1), -- Free WiFi
    (9, 3, 2), -- Breakfast Included
    (9, 4, 4); -- Gym

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (9, 1, 13), -- Image 13
    (9, 2, 14), -- Image 14
    (9, 3, 15), -- Image 15
    (9, 4, 16); -- Image 16

-- Rooms for 'Sunset Retreat' (ID 10)
INSERT INTO room_convenience (hotel_id, room_nr, convenience_id)
VALUES
    (10, 1, 5), -- Parking
    (10, 2, 3), -- Swimming Pool
    (10, 3, 2), -- Breakfast Included
    (10, 4, 1); -- Free WiFi

INSERT INTO room_image (hotel_id, room_nr, image_id)
VALUES
    (10, 1, 17), -- Image 17
    (10, 2, 18), -- Image 18
    (10, 3, 19), -- Image 19
    (10, 4, 20); -- Image 20
