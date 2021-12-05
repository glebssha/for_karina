package com.shabuldaev;

import com.shabuldaev.service.db.*;

import com.shabuldaev.service.db.DbInput;
import org.h2.jdbcx.JdbcConnectionPool;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public final class App {
    private App() {
    }
    private static DataSource s = JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1",
            "", "");
    private static SimpleJdbcTemplate source = new SimpleJdbcTemplate(
            s);

    public static void main(String[] args) throws SQLException, IOException {
        DbInit db = new DbInit(source);
        db.create();
        DbInput dbf = new DbInput(source);
        dbf.InputDB();
        DbQueries q = new DbQueries(source);
        q.execute_query7("2016-12-10", "2017-10-15");
    }
}
