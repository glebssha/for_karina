package com.shabuldaev.service.dao;

import lombok.AllArgsConstructor;
import com.shabuldaev.domain.Airports;
import com.shabuldaev.service.db.SimpleJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class AirportsDao {
    private final SimpleJdbcTemplate source;

    private Airports createAirports(ResultSet resultSet) throws SQLException {
        return new Airports(resultSet.getString("airport_code"),
                resultSet.getString("airport_name"),
                resultSet.getString("city"),
                resultSet.getString("coordinates"),
                resultSet.getString("timezone"));
    }

    public void saveAirports(Collection<Airports> airports) throws SQLException {
        source.preparedStatement("insert into airports(airport_code, airport_name, city, coordinates, timezone) values (?, ?, ?, ?, ?)",
                insertAirports -> {
            for (Airports airport : airports) {
                insertAirports.setString(1, airport.getAirportCode());
                insertAirports.setString(2, airport.getAirportName().toString());
                insertAirports.setString(3, airport.getCity().toString());
                insertAirports.setString(4, airport.getCoordinates()); //CHECK!!!
                insertAirports.setString(5, airport.getTimezone());
                insertAirports.execute();
            }
        });
    }

    public Set<Airports> getAirports() throws SQLException {
        return source.statement(stmt -> {
            Set<Airports> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from airports");
            while (resultSet.next()) {
                result.add(createAirports(resultSet));
            }
            return result;
        });
    }
}