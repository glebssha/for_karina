package com.shabuldaev.service.db;

import com.shabuldaev.domain.Bookings;
import com.shabuldaev.domain.Tickets;
import com.shabuldaev.service.db.FormatQuery1;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;

import com.shabuldaev.service.dao.BookingsDao;
import com.shabuldaev.service.dao.TicketsDao;
import com.shabuldaev.service.db.SimpleJdbcTemplate;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.shabuldaev.service.db.DbInit.getSQL;


@AllArgsConstructor
public class DbQueries {
    private final SimpleJdbcTemplate source;

    /**
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<String> execute_query1() throws SQLException, IOException {
        String sql = getSQL("query_1.sql");
        ArrayList<String> result = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet k = stmt.executeQuery(sql);
            while (k.next()) {
                String[] city_name = k.getString("city").split("\"");
                result.add(city_name[4] + " : " + k.getString("Airports_List"));
            }
        });
        System.out.println(result);
        return result;
    }

    /**
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<String> execute_query2() throws IOException, SQLException {
        String sql = getSQL("query_2.sql");
        ArrayList<String> result = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet k = stmt.executeQuery(sql);
            while (k.next()) {
                String[] city_name = k.getString("city").split("\"");
                result.add(city_name[4] + " - " + k.getString("Cancelled_num"));
            }
        });
        return result;
    }

    /**
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<String> execute_query3() throws IOException, SQLException {
        String sql = getSQL("query_3.sql");
        ArrayList<String> result = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet k = stmt.executeQuery(sql);
            while (k.next()) {
                String[] city_name1 = k.getString("departure").split("\"");
                String[] city_name2 = k.getString("arrival").split("\"");
                result.add(city_name1[4] + " -> " + city_name2[4] + ". Avg time: " + Float.parseFloat(k.getString("avg_time")));

            }
        });
        return result;
    }

    /**
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<String> execute_query4() throws IOException, SQLException {
        String sql = getSQL("query_4.sql");
        ArrayList<String> result = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet k = stmt.executeQuery(sql);
            while (k.next()) {
                int t = Integer.parseInt(k.getString(1));
                result.add(Month.of(t).toString() + " - " + Integer.parseInt(k.getString("Cancelled")));
            }
        });
        return result;
    }

    /**
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<String> execute_query5() throws IOException, SQLException {
        String sql = getSQL("query_5.sql");
        ArrayList<String> result = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet k = stmt.executeQuery(sql);
            while (k.next()) {
                int t = Integer.parseInt(k.getString(1));
                result.add(DayOfWeek.of(t).toString() + ": in - " + Integer.parseInt(k.getString("incoming")) +
                        ", out - " + Integer.parseInt(k.getString("outcoming")));
            }
        });
        //System.out.println(result);
        return result;
    }

    /**
     * @param number
     * @throws IOException
     * @throws SQLException
     */
    public void execute_query6(String number) throws IOException, SQLException {
        String sql = getSQL("query_6.sql");
        source.preparedStatement(sql, stmt -> {
            stmt.setString(1, number);
            stmt.execute();
        });
    }


    public ArrayList<String> execute_query7(String outbreak_date, String vaccine_date)
            throws IOException, SQLException {
        String sql = getSQL("query_7_select.sql");
        String sql_upd = getSQL("query_7_updt.sql");
        ArrayList<String> result = new ArrayList<>();
        source.connection(conn -> {
            conn.setAutoCommit(false);
            PreparedStatement stmt1 = conn.prepareStatement(sql);
            PreparedStatement stmt2 = conn.prepareStatement(sql_upd);
            stmt1.setDate(1, Date.valueOf(outbreak_date));
            stmt1.setDate(2, Date.valueOf(vaccine_date));
            stmt2.setDate(1, Date.valueOf(outbreak_date));
            stmt2.setDate(2, Date.valueOf(vaccine_date));
            ResultSet k = stmt1.executeQuery();
            while (k.next()) {
                result.add(k.getString("day_cancelled") + " " + Double.valueOf(k.getString("money_loss")).longValue());
            }
            System.out.println(result);
            stmt2.execute();
            conn.commit();
        });
        return result;
    }
}
