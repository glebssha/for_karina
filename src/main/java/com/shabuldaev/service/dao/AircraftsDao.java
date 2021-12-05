package com.shabuldaev.service.dao;

import lombok.AllArgsConstructor;
import com.shabuldaev.domain.Aircrafts;
import com.shabuldaev.service.db.SimpleJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class AircraftsDao {
    private final SimpleJdbcTemplate source;

    private Aircrafts createAircrafts(ResultSet resultSet) throws SQLException {
        return new Aircrafts(resultSet.getString("aircraft_code"),
                resultSet.getString("model"),
                resultSet.getInt("range"));
    }

    public void saveAircrafts(Collection<Aircrafts> aircrafts) throws SQLException {
        source.preparedStatement("insert into aircrafts(aircraft_code, model, range) values (?, ?, ?)", insertAircrafts -> {
            for (Aircrafts aircraft : aircrafts) {
                insertAircrafts.setString(1, aircraft.getAircraftCode());
                insertAircrafts.setString(2, aircraft.getModel());
                insertAircrafts.setInt(3, aircraft.getRange());
                insertAircrafts.execute();
            }
        });
    }

    public Set<Aircrafts> getAircrafts() throws SQLException {
        return source.statement(stmt -> {
            Set<Aircrafts> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from aircrafts");
            while (resultSet.next()) {
                result.add(createAircrafts(resultSet));
            }
            return result;
        });
    }
}