package com.shabuldaev;

import com.shabuldaev.service.db.DbInit;
import com.shabuldaev.service.db.SimpleJdbcTemplate;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import com.shabuldaev.service.db.DbInput;
import org.h2.jdbcx.JdbcConnectionPool;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class AppTest {
    private static DataSource s = JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1",
            "", "");
    private static SimpleJdbcTemplate source = new SimpleJdbcTemplate(
            s);
    @Test
    void helloTest() throws SQLException, IOException {

        DbInit db = new DbInit(source);
        db.create();
        DbInput dbf = new DbInput(source);
        dbf.InputDB();
    }
}
