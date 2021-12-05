package com.shabuldaev.service.dao;

import lombok.AllArgsConstructor;
import com.shabuldaev.domain.Bookings;
import com.shabuldaev.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class BookingsDao {
    private final SimpleJdbcTemplate source;

    private Bookings createBookings(ResultSet resultSet) throws SQLException {
        return new Bookings(resultSet.getString("book_ref"),
                resultSet.getTimestamp("book_date"),
                resultSet.getBigDecimal("total_amount"));
    }

    public void saveBookings(Collection<Bookings> bookings) throws SQLException {
        source.preparedStatement("insert into bookings(book_ref, book_date, total_amount) values (?, ?, ?)",
                insertBookings -> {
            for (Bookings booking : bookings) {
                insertBookings.setString(1, booking.getBookRef());
                insertBookings.setTimestamp(2, booking.getBookDate());
                insertBookings.setBigDecimal(3, booking.getTotalAmount());
                insertBookings.execute();
            }
        });
    }

    public Set<Bookings> getBookings() throws SQLException {
        return source.statement(stmt -> {
            Set<Bookings> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from bookings");
            while (resultSet.next()) {
                result.add(createBookings(resultSet));
            }
            return result;
        });
    }
}