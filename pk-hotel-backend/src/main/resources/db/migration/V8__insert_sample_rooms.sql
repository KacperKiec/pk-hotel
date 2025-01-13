-- Insert sample data for 'room' table
-- Assuming we already have hotel data in the 'hotel' table (IDs 1-10)

-- Rooms for 'Hotel Blue Sky' (ID 1)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (1, 1, 2, 120.00, 'HIGH', 'Double room with sea view'),
    (1, 2, 2, 100.00, 'AVERAGE', 'Double room with balcony'),
    (1, 3, 4, 150.00, 'HIGH', 'Family room with two bedrooms'),
    (1, 4, 2, 80.00, 'LOW', 'Single room with city view');

-- Rooms for 'Hotel Sunshine' (ID 2)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (2, 1, 2, 90.00, 'AVERAGE', 'Double room with city view'),
    (2, 2, 1, 50.00, 'LOW', 'Single room with a small balcony'),
    (2, 3, 4, 140.00, 'HIGH', 'Family room with a large living room'),
    (2, 4, 3, 120.00, 'AVERAGE', 'Triple room with park view');

-- Rooms for 'Grand Palace Hotel' (ID 3)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (3, 1, 2, 110.00, 'AVERAGE', 'Double room with a view of the Colosseum'),
    (3, 2, 2, 130.00, 'HIGH', 'Double room with a balcony and city view'),
    (3, 3, 4, 180.00, 'HIGH', 'Family room with two bathrooms'),
    (3, 4, 2, 85.00, 'LOW', 'Single room with garden view');

-- Rooms for 'Mountain View Resort' (ID 4)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (4, 1, 2, 140.00, 'HIGH', 'Room with mountain view'),
    (4, 2, 3, 110.00, 'AVERAGE', 'Triple room with a fireplace'),
    (4, 3, 4, 200.00, 'HIGH', 'Family room with a spacious living area'),
    (4, 4, 2, 75.00, 'LOW', 'Single room in the mountains');

-- Rooms for 'Ocean Breeze Hotel' (ID 5)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (5, 1, 2, 125.00, 'HIGH', 'Double room with ocean view'),
    (5, 2, 3, 150.00, 'HIGH', 'Family room with a large balcony'),
    (5, 3, 2, 90.00, 'AVERAGE', 'Room with beach view'),
    (5, 4, 4, 200.00, 'HIGH', 'Room with private pool');

-- Rooms for 'Lake Side Hotel' (ID 6)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (6, 1, 2, 100.00, 'AVERAGE', 'Room with lake view'),
    (6, 2, 1, 70.00, 'LOW', 'Single room with forest view'),
    (6, 3, 4, 160.00, 'HIGH', 'Family room with a large kitchen'),
    (6, 4, 2, 120.00, 'AVERAGE', 'Double room with balcony overlooking the lake');

-- Rooms for 'Desert Mirage Resort' (ID 7)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (7, 1, 2, 140.00, 'HIGH', 'Double room with desert view'),
    (7, 2, 2, 120.00, 'AVERAGE', 'Double room with a small pool'),
    (7, 3, 4, 180.00, 'HIGH', 'Family room with private terrace'),
    (7, 4, 1, 85.00, 'LOW', 'Single room with city view');

-- Rooms for 'City Lights Hotel' (ID 8)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (8, 1, 2, 110.00, 'AVERAGE', 'Double room with city lights view'),
    (8, 2, 1, 60.00, 'LOW', 'Single room with street view'),
    (8, 3, 3, 130.00, 'AVERAGE', 'Triple room with view of the skyline'),
    (8, 4, 4, 160.00, 'HIGH', 'Family suite with panoramic city view');

-- Rooms for 'Green Fields Hotel' (ID 9)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (9, 1, 2, 120.00, 'AVERAGE', 'Double room with garden view'),
    (9, 2, 3, 150.00, 'HIGH', 'Triple room with balcony and park view'),
    (9, 3, 4, 180.00, 'HIGH', 'Family room with two bathrooms and garden view'),
    (9, 4, 2, 80.00, 'LOW', 'Single room with park view');

-- Rooms for 'Sunset Retreat' (ID 10)
INSERT INTO room (hotel_id, room_nr, places, price, standard, description)
VALUES
    (10, 1, 2, 130.00, 'HIGH', 'Double room with sunset view'),
    (10, 2, 1, 90.00, 'AVERAGE', 'Single room with beach view'),
    (10, 3, 3, 160.00, 'HIGH', 'Triple room with large balcony overlooking the ocean'),
    (10, 4, 2, 110.00, 'AVERAGE', 'Double room with pool view');

