package sqllite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component("connection")
public class SQLiteConnection {

    private final Connection connection;

    @Autowired
    public SQLiteConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }
}
