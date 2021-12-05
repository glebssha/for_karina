CREATE TYPE IF NOT EXISTS "JSONB" AS VARCHAR(255);
CREATE TYPE IF NOT EXISTS "POINT" AS VARCHAR(255);

CREATE TABLE aircrafts (
                           aircraft_code CHAR(3) NOT NULL,
                           model JSONB NOT NULL,
                           range INT NOT NULL
);

CREATE TABLE airports (
                          airport_code CHAR(3) NOT NULL,
                          airport_name JSONB NOT NULL,
                          city JSONB NOT NULL,
                          coordinates POINT NOT NULL,
                          timezone TEXT NOT NULL
);

CREATE TABLE boarding_passes (
                                 ticket_no CHAR(13) NOT NULL,
                                 flight_id INT NOT NULL,
                                 boarding_no INT NOT NULL,
                                 seat_no VARCHAR(4) NOT NULL
);

CREATE TABLE bookings (
                          book_ref CHAR(6) NOT NULL,
                          book_date TIMESTAMP NOT NULL,
                          total_amount NUMERIC(10, 2) NOT NULL
);

CREATE TABLE flights (
                         flight_id IDENTITY NOT NULL,
                         flight_no CHAR(6) NOT NULL,
                         scheduled_departure TIMESTAMP NOT NULL,
                         scheduled_arrival TIMESTAMP NOT NULL,
                         departure_airport CHAR(3) NOT NULL,
                         arrival_airport CHAR(3) NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         aircraft_code CHAR(3) NOT NULL,
                         actual_departure TIMESTAMP,
                         actual_arrival TIMESTAMP
);

CREATE TABLE seats (
                       aircraft_code CHAR(3) NOT NULL,
                       seat_no VARCHAR(4) NOT NULL,
                       fare_conditions VARCHAR(10) NOT NULL
);

CREATE TABLE ticket_flights (
                                ticket_no CHAR(13) NOT NULL,
                                flight_id INT NOT NULL,
                                fare_conditions VARCHAR(10) NOT NULL,
                                amount NUMERIC(10, 2) NOT NULL
);

CREATE TABLE tickets (
                         ticket_no CHAR(13) NOT NULL,
                         book_ref CHAR(6) NOT NULL,
                         passenger_id VARCHAR(20) NOT NULL,
                         passenger_name TEXT NOT NULL,
                         contact_data TEXT
);