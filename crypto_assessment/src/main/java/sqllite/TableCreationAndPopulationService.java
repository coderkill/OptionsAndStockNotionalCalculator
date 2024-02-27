package sqllite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.sql.SQLException;
import java.sql.Statement;

//@Component
@Configurable(preConstruction=true)
public class TableCreationAndPopulationService {

    public void createInstrumentTable(Statement statement) throws SQLException {
        // Create a new table
        String createTableQuery = "CREATE TABLE instrument (\n" +
                "   id INT PRIMARY KEY AUTOINCREMENT,\n" + //AUTOINCREMENT is slow for sqllite in realtime, hence will not prefer using this in real time data scenario since its static the overhead is acceptable
                "   ticker VARCHAR(60),\n" +
                "   security_type VARCHAR(10),\n" + // -- 'Stock', 'Call', 'Put'
                "   strike DOUBLE,\n" +
                "   maturity DATE\n," +
                "lot_size INT," +
                "   trading_currency VARCHAR(255)," +
                "   underlying_ticker VARCHAR(20)" +
                ");";
        statement.execute(createTableQuery);
    }

    public void createFxTable(Statement statement) throws SQLException {
        // Create a new table
        String createTableQuery = "CREATE TABLE fx_bar_timestamp (\n" +
                "   id INT PRIMARY KEY AUTOINCREMENT,\n" + //AUTOINCREMENT is slow for sqllite in realtime, hence will not prefer using this in real time data scenario since its static the overhead is acceptable
                "   currency_base VARCHAR(60),\n" +
                "   currency_to VARCHAR(60),\n" +
                "   fx_rate DOUBLE,\n" + // -- 'Stock', 'Call', 'Put'
                "   timestamp_date DATE\n," +
                ");";
        statement.execute(createTableQuery);
    }

    private void insertStockInstrumentTable(Statement statement) throws SQLException {
        // Insert into table
        String insertDataQueryAaple = "INSERT INTO instrument (ticker,security_type," +
                " trading_currency, underlying_ticker) VALUES ('AAPL', 'Stock' ,'USD', 'AAPL')";
        String insertDataQueryTesla = "INSERT INTO instrument (ticker,security_type," +
                " trading_currency, underlying_ticker) VALUES ('TSLA', 'Stock' ,'USD', 'TSLA')";
        statement.execute(insertDataQueryAaple);
        statement.execute(insertDataQueryTesla);
    }

    private void insertFxTable(Statement statement) throws SQLException {
        // Insert into table
        String insertDataQueryAaple = "INSERT INTO fx_bar_timestamp (currency_base,currency_to," +
                " fx_rate, timestamp_date) VALUES ('USD', 'USD' ,40.0, 'AAPL')";
        String insertDataQueryTesla = "INSERT INTO fx_bar_timestamp (ticker,security_type," +
                " trading_currency, underlying_ticker) VALUES ('TSLA', 'Stock' ,'USD', 'TSLA')";
        statement.execute(insertDataQueryAaple);
        statement.execute(insertDataQueryTesla);
    }

    private void insertOptionsInstrumentTable(Statement statement) throws SQLException {
        // Insert into table
        String insertDataQueryAapleCall = "INSERT INTO instrument (ticker,security_type, strike, maturity," +
                " trading_currency, underlying_ticker, lot_size) VALUES ('AAPL-OCT-2020-110-C', 'Call', 110.00, '2020-12-01', 'USD', 'AAPL', 10)";
        String insertDataQueryAaplePut =  "INSERT INTO instrument (ticker,security_type, strike, maturity," +
                " trading_currency, underlying_ticker, lot_size) VALUES ('AAPL-OCT-2020-110-P', 'Put', 110.00, '2020-12-01', 'USD', 'AAPL', 10)";
        String insertDataQueryTeslaCall = "INSERT INTO instrument (ticker,security_type, strike, maturity," +
                " trading_currency, underlying_ticker, lot_size) VALUES ('TELSA-NOV-2020-400-C', 'Call', 400.00, '2020-12-01', 'USD', 'TSLA', 5)";
        String insertDataQueryTeslaPut =  "INSERT INTO instrument (ticker,security_type, strike, maturity," +
                " trading_currency, underlying_ticker, lot_size) VALUES ('TSLA-DEC-2020-400-P', 'Put', 400.00, '2020-12-01', 'USD', 'TSLA',5)";
        statement.execute(insertDataQueryAapleCall);
        statement.execute(insertDataQueryAaplePut);
        statement.execute(insertDataQueryTeslaCall);
        statement.execute(insertDataQueryTeslaPut);
    }

    @Autowired
    TableCreationAndPopulationService(SQLiteConnection connection) throws SQLException {
        Statement statement = connection.getConnection().createStatement();
        createInstrumentTable(statement);
        createFxTable(statement);
        insertStockInstrumentTable(statement);
        insertOptionsInstrumentTable(statement);
        insertFxTable(statement);
    }
}
