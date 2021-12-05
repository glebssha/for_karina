package com.shabuldaev.service.db;

import com.shabuldaev.domain.*;
import com.shabuldaev.service.dao.*;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;


@AllArgsConstructor
public class DbInput {
    static String aircraft_url = "https://storage.yandexcloud.net/airtrans-small/aircrafts.csv";
    static String airports_url = "https://storage.yandexcloud.net/airtrans-small/airports.csv";
    static String bookings_url = "https://storage.yandexcloud.net/airtrans-small/bookings.csv";
    static String bpasses_url = "https://storage.yandexcloud.net/airtrans-small/boarding_passes.csv";
    static String flights_url = "https://storage.yandexcloud.net/airtrans-small/flights.csv";
    static String seats_url = "https://storage.yandexcloud.net/airtrans-small/seats.csv";
    static String tickets_url = "https://storage.yandexcloud.net/airtrans-small/tickets.csv";
    static String tflights_url = "https://storage.yandexcloud.net/airtrans-small/ticket_flights.csv";
    private final SimpleJdbcTemplate source;

    public void InputDB() throws SQLException, IOException {
        input_aircrafts();
        input_airports();
        input_flights();
        input_tickets();
        input_bookings();
        input_boardingpasses();
        input_seats();
    }

    private void input_aircrafts() throws IOException, SQLException {
        URL url = new URL(aircraft_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<Aircrafts> aircrafts = new ArrayList<>();
            String buff;
            while ((buff = in.readLine()) != null) {
                String[] l = buff.split(",");
                aircrafts.add(new Aircrafts(l[0],
                        (l[1] + l[2]).replaceAll("\"\"", "\""),
                        Integer.parseInt(l[3])));
            }
            new AircraftsDao(source).saveAircrafts(aircrafts);
        }
    }

    private void input_airports() throws IOException, SQLException {
        URL url = new URL(airports_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<Airports> airports = new ArrayList<>();
            String buff;
            while ((buff = in.readLine()) != null) {
                String[] l = buff.split(",");
                airports.add(new Airports(l[0],
                        (l[1] + l[2]).replaceAll("\"\"", "\""),
                        (l[3] + l[4]).replaceAll("\"\"", "\""),
                        l[5], l[6]));
            }
            new AirportsDao(source).saveAirports(airports);
        }
    }

    private void input_boardingpasses() throws IOException, SQLException {
        URL url = new URL(bpasses_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<BoardingPasses> passes = new ArrayList<>();
            String buff;
            while ((buff = in.readLine()) != null) {
                String[] l = buff.split(",");
                passes.add(new BoardingPasses(l[0], Integer.parseInt(l[1]),
                        Integer.parseInt(l[2]), l[3]));
            }
            new BoardingPassesDao(source).saveBoardingPasses(passes);
        }
    }

    private void input_bookings() throws IOException, SQLException {
        URL url = new URL(bookings_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<Bookings> bookings = new ArrayList<>();
            String buff;

            while ((buff = in.readLine()) != null) {
                String[] l = buff.split(",");
                bookings.add(new Bookings(l[0], Timestamp.valueOf(l[1].split("\\+")[0]), new BigDecimal(l[2])));
            }
            new BookingsDao(source).saveBookings(bookings);
        }
    }

    private void input_flights() throws IOException, SQLException {

        URL url = new URL(flights_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<Flights> flights = new ArrayList<>();
            String buff;
            while ((buff = in.readLine()) != null) {

                String[] l = buff.split(",");
                Timestamp t1 = null;
                Timestamp t2 = null;
                if (l.length == 10) {
                    t1 = Timestamp.valueOf(l[8].split("\\+")[0]);
                    t2 = Timestamp.valueOf(l[9].split("\\+")[0]);
                }
                if (l.length == 9) {
                    t1 = Timestamp.valueOf(l[8].split("\\+")[0]);
                }
                flights.add(new Flights(Long.parseLong(l[0]), l[1],
                        Timestamp.valueOf(l[2].split("\\+")[0]),
                        Timestamp.valueOf(l[3].split("\\+")[0]),
                        l[4], l[5], l[6], l[7], t1, t2));
            }
            new FlightsDao(source).saveFlights(flights);
        }
    }

    private void input_seats() throws IOException, SQLException {
        URL url = new URL(seats_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<Seats> seats = new ArrayList<>();
            String buff;

            while ((buff = in.readLine()) != null) {
                String[] l = buff.split(",");
                seats.add(new Seats(l[0], l[1], l[2]));
            }
            new SeatsDao(source).saveSeats(seats);
        }
    }

    private void fillTicketFlights() throws IOException, SQLException {
        URL url = new URL(tflights_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<TicketFlights> tflights = new ArrayList<>();
            String buff;

            while ((buff = in.readLine()) != null) {
                String[] l = buff.split(",");
                tflights.add(new TicketFlights(l[0],
                        Integer.parseInt(l[1]),
                        l[2],
                        new BigDecimal(l[3])));
            }
            new TicketFlightsDao(source).saveTicketFlights(tflights);
        }
    }

    private void input_tickets() throws IOException, SQLException {
        URL url = new URL(tickets_url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            ArrayList<Tickets> tickets = new ArrayList<>();
            String buff;

            while ((buff = in.readLine()) != null) {
                String[] l = buff.split(",");
                String cdata = null;
                StringBuilder contact_data = new StringBuilder("");
                if (l.length > 4) {
                    for (int i = 4; i < l.length - 1; ++i) {
                        contact_data.append(l[i]).append(",");
                    }
                    cdata = contact_data.toString().replaceAll("\"\"", "\"");
                }
                tickets.add(new Tickets(l[0], l[1], l[2], l[3], cdata));
            }
            new TicketsDao(source).saveTickets(tickets);
        }
    }
}