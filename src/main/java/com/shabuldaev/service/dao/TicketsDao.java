package com.shabuldaev.service.dao;

import lombok.AllArgsConstructor;
import com.shabuldaev.domain.Tickets;
import com.shabuldaev.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class TicketsDao {
    private final SimpleJdbcTemplate source;

    private Tickets createTickets(ResultSet resultSet) throws SQLException {
        return new Tickets(resultSet.getString("ticket_no"),
                resultSet.getString("book_ref"),
                resultSet.getString("passenger_id"),
                resultSet.getString("passenger_name"),
                resultSet.getString("contact_data"));
    }

    public void saveTickets(Collection<Tickets> tickets) throws SQLException {
        source.preparedStatement("insert into tickets(ticket_no, book_ref, passenger_id, passenger_name, contact_data) values (?, ?, ?, ?, ?)",
                insertTickets -> {
            for (Tickets ticket : tickets) {
                insertTickets.setString(1, ticket.getTicketNo());
                insertTickets.setString(2, ticket.getBookRef());
                insertTickets.setString(3, ticket.getPassengerId());
                insertTickets.setString(4, ticket.getPassengerName());
                insertTickets.setString(5, ticket.getContactData());
                insertTickets.execute();
            }
        });
    }

    public Set<Tickets> getTickets() throws SQLException {
        return source.statement(stmt -> {
            Set<Tickets> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from tickets");
            while (resultSet.next()) {
                result.add(createTickets(resultSet));
            }
            return result;
        });
    }
}