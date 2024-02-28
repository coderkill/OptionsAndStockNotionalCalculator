package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Service
public class TableCreationAndPopulationService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TableCreationAndPopulationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        try {
            createTables();
            insertData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        createTable("instrument",
                "id INTEGER PRIMARY KEY AUTOINCREMENT, ticker VARCHAR(60) UNIQUE, security_type VARCHAR(10), strike DOUBLE, maturity DATE, lot_size INT, trading_currency VARCHAR(255), underlying_ticker VARCHAR(20)"
        );

        createTable("fx_bar_timestamp",
                "id INTEGER PRIMARY KEY AUTOINCREMENT, currency_base VARCHAR(60), currency_from VARCHAR(60) UNIQUE, fx_rate DOUBLE, timestamp_date DATE"
        );

        createTable("price_bar_timestamp",
                "id INTEGER PRIMARY KEY AUTOINCREMENT, ticker VARCHAR(60) UNIQUE, price DOUBLE, timestamp_date DATE"
        );
    }

    private void createTable(String tableName, String columns) throws SQLException {
        String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (%s);", tableName, columns);
        jdbcTemplate.execute(createTableQuery);
    }

    private void insertData() throws SQLException {
        List<String> stockDataQueries = Arrays.asList(
                "INSERT OR IGNORE INTO instrument (ticker,security_type, trading_currency, underlying_ticker, lot_size) VALUES ('AAPL', 'Stock' ,'USD', 'AAPL', 1)",
                "INSERT OR IGNORE INTO instrument (ticker,security_type, trading_currency, underlying_ticker, lot_size) VALUES ('TSLA', 'Stock' ,'USD', 'TSLA', 1)"
        );

        List<String> fxDataQueries = Arrays.asList(
                "INSERT OR IGNORE INTO fx_bar_timestamp (currency_base,currency_from, fx_rate, timestamp_date) VALUES ('USD', 'USD' ,1.0, '2020-02-27')",
                "INSERT OR IGNORE INTO fx_bar_timestamp (currency_base,currency_from, fx_rate, timestamp_date) VALUES ('USD', 'HKD' ,40.0, '2020-02-27')"
        );

        List<String> priceDataQueries = Arrays.asList(
                "INSERT OR IGNORE INTO price_bar_timestamp (ticker, price, timestamp_date) VALUES ('AAPL-OCT-2020-110-C', 100.0, '2020-02-27')",
                "INSERT OR IGNORE INTO price_bar_timestamp (ticker, price, timestamp_date) VALUES ('AAPL-OCT-2020-110-P', 20.0, '2020-02-27')",
                "INSERT OR IGNORE INTO price_bar_timestamp (ticker, price, timestamp_date) VALUES ('AAPL', 200.0, '2020-02-27')",
                "INSERT OR IGNORE INTO price_bar_timestamp (ticker, price, timestamp_date) VALUES ('TSLA-NOV-2020-400-C',20.0, '2020-02-27')",
                "INSERT OR IGNORE INTO price_bar_timestamp (ticker, price, timestamp_date) VALUES ('TSLA-DEC-2020-400-P', 120.0, '2020-02-27')",
                "INSERT OR IGNORE INTO price_bar_timestamp (ticker, price, timestamp_date) VALUES ('TSLA', 210.0, '2020-02-27')"
        );

        List<String> optionsDataQueries = Arrays.asList(
                "INSERT OR IGNORE INTO instrument (ticker,security_type, strike, maturity, trading_currency, underlying_ticker, lot_size) VALUES ('AAPL-OCT-2020-110-C', 'Call', 110.00, '2020-12-01', 'USD', 'AAPL', 10)",
                "INSERT OR IGNORE INTO instrument (ticker,security_type, strike, maturity, trading_currency, underlying_ticker, lot_size) VALUES ('AAPL-OCT-2020-110-P', 'Put', 110.00, '2020-12-01', 'USD', 'AAPL', 10)",
                "INSERT OR IGNORE INTO instrument (ticker,security_type, strike, maturity, trading_currency, underlying_ticker, lot_size) VALUES ('TSLA-NOV-2020-400-C', 'Call', 400.00, '2020-12-01', 'USD', 'TSLA', 5)",
                "INSERT OR IGNORE INTO instrument (ticker,security_type, strike, maturity, trading_currency, underlying_ticker, lot_size) VALUES ('TSLA-DEC-2020-400-P', 'Put', 400.00, '2020-12-01', 'USD', 'TSLA',5)"
        );

        executeBatchQueries(stockDataQueries);
        executeBatchQueries(fxDataQueries);
        executeBatchQueries(priceDataQueries);
        executeBatchQueries(optionsDataQueries);
    }

    private void executeBatchQueries(List<String> queries) {
        queries.forEach(jdbcTemplate::update);
    }
}
