-- Insert sample data for 'hotel' table
INSERT INTO hotel (name, owner, register_date, country, city, address)
VALUES
    ('Hotel Blue Sky', 'John Doe', '2020-05-10', 'Poland', 'Warsaw', 'Main Street 10'),
    ('Hotel Sunshine', 'Alice Smith', '2018-03-22', 'Germany', 'Berlin', 'Baker Street 24'),
    ('Grand Palace Hotel', 'Robert Brown', '2017-07-15', 'Italy', 'Rome', 'Via Roma 15'),
    ('Mountain View Resort', 'Charlie Davis', '2019-01-05', 'Switzerland', 'Zurich', 'Mountain Peak 9'),
    ('Ocean Breeze Hotel', 'Martha Johnson', '2021-06-17', 'Spain', 'Barcelona', 'Seafront Avenue 3'),
    ('Lake Side Hotel', 'George Wilson', '2022-09-11', 'Poland', 'Krakow', 'Lakeview Road 5'),
    ('Desert Mirage Resort', 'Sara Martinez', '2016-12-30', 'UAE', 'Dubai', 'Desert Street 45'),
    ('City Lights Hotel', 'David Lee', '2015-10-22', 'USA', 'New York', 'Broadway 58'),
    ('Green Fields Hotel', 'Olivia Harris', '2023-02-01', 'France', 'Paris', 'Boulevard Saint-Germain 21'),
    ('Sunset Retreat', 'Liam Clark', '2018-08-08', 'Australia', 'Sydney', 'Harbour Road 7');

-- Insert sample data for 'convenience' table
INSERT INTO convenience (name)
VALUES
    ('Free WiFi'),
    ('Breakfast Included'),
    ('Swimming Pool'),
    ('Gym'),
    ('Parking');

-- Insert sample data for 'extra' table
INSERT INTO extra (name, price_per_day)
VALUES
    ('Airport Transfer', 30.00),
    ('Spa Access', 50.00),
    ('Extra Bed', 20.00),
    ('Late Checkout', 15.00),
    ('Room Upgrade', 40.00);

-- Dodanie zdjęć do tabeli 'image'
INSERT INTO image (path)
VALUES
    ('/assets/room1.jpg'),
    ('/assets/room2.jpg'),
    ('/assets/room3.jpg'),
    ('/assets/room4.jpg'),
    ('/assets/room5.jpg'),
    ('/assets/room6.jpg'),
    ('/assets/room7.jpg'),
    ('/assets/room8.jpg'),
    ('/assets/room9.jpg'),
    ('/assets/room10.jpg'),
    ('/assets/room11.jpg'),
    ('/assets/room12.jpg'),
    ('/assets/room13.jpg'),
    ('/assets/room14.jpg'),
    ('/assets/room15.jpg'),
    ('/assets/room16.jpg'),
    ('/assets/room17.jpg'),
    ('/assets/room18.jpg'),
    ('/assets/room19.jpg'),
    ('/assets/room20.jpg');