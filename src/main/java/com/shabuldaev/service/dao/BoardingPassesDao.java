package com.shabuldaev.service.dao;

import lombok.AllArgsConstructor;
import com.shabuldaev.domain.BoardingPasses;
import com.shabuldaev.service.db.SimpleJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class BoardingPassesDao {
    private final SimpleJdbcTemplate source;

    private BoardingPasses createBoardingPasses(ResultSet resultSet) throws SQLException {
        return new BoardingPasses(resultSet.getString("ticket_no"),
                resultSet.getInt("flight_id"),
                resultSet.getInt("boarding_no"),
                resultSet.getString("seat_no"));
    }

    public void saveBoardingPasses(Collection<BoardingPasses> boardingPasses) throws SQLException {
        source.preparedStatement("insert into boarding_passes(ticket_no, flight_id, boarding_no, seat_no) values (?, ?, ?, ?)",
                insertBoardingPasses -> {
            for (BoardingPasses boardingPass : boardingPasses) {
                insertBoardingPasses.setString(1, boardingPass.getTicketNo());
                insertBoardingPasses.setInt(2, boardingPass.getFlightId());
                insertBoardingPasses.setInt(3, boardingPass.getBoardingNo());
                insertBoardingPasses.setString(4, boardingPass.getSeatNo());
                insertBoardingPasses.execute();
            }
        });
    }

    public Set<BoardingPasses> getBoardingPasses() throws SQLException {
        return source.statement(stmt -> {
            Set<BoardingPasses> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from boarding_passes");
            while (resultSet.next()) {
                result.add(createBoardingPasses(resultSet));
            }
            return result;
        });
    }
}