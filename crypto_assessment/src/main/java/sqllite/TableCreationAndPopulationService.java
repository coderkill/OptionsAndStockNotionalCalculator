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
                "   currency_from VARCHAR(60),\n" +
                "   fx_rate DOUBLE,\n" + // -- 'Stock', 'Call', 'Put'
                "   timestamp_date DATE\n," +
                ");";
        statement.execute(createTableQuery);
    }


    public void createPricesTable(Statement statement) throws SQLException {
        // Create a new table
        String createTableQuery = "CREATE TABLE price_bar_timestamp (\n" +
                "   id INT PRIMARY KEY AUTOINCREMENT,\n" + //AUTOINCREMENT is slow for sqllite in realtime, hence will not prefer using this in real time data scenario since its static the overhead is acceptable
                "   ticker VARCHAR(60),\n" +
                "   price DOUBLE,\n" + // -- 'Stock', 'Call', 'Put'
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
        String insertDataQueryAaple = "INSERT INTO fx_bar_timestamp (currency_base,currency_from," +
                " fx_rate, timestamp_date) VALUES ('USD', 'USD' ,1.0, '2020-02-27')";
        String insertDataQueryTesla = "INSERT INTO fx_bar_timestamp (currency_base,currency_from," +
                " fx_rate, timestamp_date) VALUES ('USD', 'HKD' ,40.0, '2020-02-27')";
        statement.execute(insertDataQueryAaple);
        statement.execute(insertDataQueryTesla);
    }

    private void insertPriceTable(Statement statement) throws SQLException {
        // Insert into table
        String insertDataQueryAaple = "INSERT INTO price_bar_timestamp (ticker," +
                " price, timestamp_date) VALUES ('AAPL-OCT-2020-110-C', 100.0, '2020-02-27')";
        String insertDataQueryAaple2 = "INSERT INTO fx_bar_timestamp (ticker," +
                "price, timestamp_date) VALUES ('AAPL-OCT-2020-110-P', 2000.0, '2020-02-27')";
        String insertDataQueryAaple3 = "INSERT INTO fx_bar_timestamp (ticker," +
                "price, timestamp_date) VALUES ('AAPL', 2000.0, '2020-02-27')";
        String insertDataQueryTesla = "INSERT INTO fx_bar_timestamp (ticker," +
                "price, timestamp_date) VALUES ('TLSA-NOV-2020-400-C',2000.0, '2020-02-27')";
        String insertDataQueryTesla2 = "INSERT INTO fx_bar_timestamp (ticker," +
                " price, timestamp_date) VALUES ('TSLA-DEC-2020-400-P', 2000.0, '2020-02-27')";
        String insertDataQueryTesla3 = "INSERT INTO fx_bar_timestamp (ticker," +
                " price, timestamp_date) VALUES ('TSLA', 2000.0, '2020-02-27')";
        statement.execute(insertDataQueryAaple);
        statement.execute(insertDataQueryAaple2);
        statement.execute(insertDataQueryAaple3);
        statement.execute(insertDataQueryTesla);
        statement.execute(insertDataQueryTesla2);
        statement.execute(insertDataQueryTesla3);
    }

    private void insertOptionsInstrumentTable(Statement statement) throws SQLException {
        // Insert into table
        String insertDataQueryAapleCall = "INSERT INTO instrument (ticker,security_type, strike, maturity," +
                " trading_currency, underlying_ticker, lot_size) VALUES ('AAPL-OCT-2020-110-C', 'Call', 110.00, '2020-12-01', 'USD', 'AAPL', 10)";
        String insertDataQueryAaplePut =  "INSERT INTO instrument (ticker,security_type, strike, maturity," +
                " trading_currency, underlying_ticker, lot_size) VALUES ('AAPL-OCT-2020-110-P', 'Put', 110.00, '2020-12-01', 'USD', 'AAPL', 10)";
        String insertDataQueryTeslaCall = "INSERT INTO instrument (ticker,security_type, strike, maturity," +
                " trading_currency, underlying_ticker, lot_size) VALUES ('TLSA-NOV-2020-400-C', 'Call', 400.00, '2020-12-01', 'USD', 'TSLA', 5)";
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
        createPricesTable(statement);
        insertStockInstrumentTable(statement);
        insertOptionsInstrumentTable(statement);
        insertFxTable(statement);
        insertPriceTable(statement);
    }
}
