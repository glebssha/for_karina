package com.shabuldaev.service.dao;

import lombok.AllArgsConstructor;
import com.shabuldaev.domain.Flights;
import com.shabuldaev.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class FlightsDao {
    private final SimpleJdbcTemplate source;

    private Flights createFlights(ResultSet resultSet) throws SQLException {
        return new Flights(resultSet.getLong("flight_id"),
                resultSet.getString("flight_no"),
                resultSet.getTimestamp("scheduled_departure"),
                resultSet.getTimestamp("scheduled_arrival"),
                resultSet.getString("departure_airport"),
                resultSet.getString("arrival_airport"),
                resultSet.getString("status"),
                resultSet.getString("aircraft_code"),
                resultSet.getTimestamp("actual_departure"),
                resultSet.getTimestamp("actual_arrival"));
    }

    public void saveFlights(Collection<Flights> flights) throws SQLException {
        source.preparedStatement("insert into flights(flight_id, flight_no, scheduled_departure, scheduled_arrival," +
                        "departure_airport, arrival_airport, status, aircraft_code, actual_departure, actual_arrival) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                insertFlights -> {
            for (Flights flight : flights) {
                insertFlights.setLong(1, flight.getFlightId());
                insertFlights.setString(2, flight.getFlightNo());
                insertFlights.setTimestamp(3, flight.getScheduledDeparture());
                insertFlights.setTimestamp(4, flight.getScheduledArrival());
                insertFlights.setString(5, flight.getDepartureAirport());
                insertFlights.setString(6, flight.getArrivalAirport());
                insertFlights.setString(7, flight.getStatus());
                insertFlights.setString(8, flight.getAircraftCode());
                insertFlights.setTimestamp(9, flight.getActualDeparture());
                insertFlights.setTimestamp(10, flight.getActualArrival());
                insertFlights.execute();
            }
        });
    }

    public Set<Flights> getFlights() throws SQLException {
        return source.statement(stmt -> {
            Set<Flights> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from flights");
            while (resultSet.next()) {
                result.add(createFlights(resultSet));
            }
            return result;
        });
    }
}