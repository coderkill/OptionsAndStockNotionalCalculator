package com.server.enrichers;

import com.server.POJO.PositionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Order(1)
@Component("dbPositionEnricher")
public class DbPositionsEnricher implements Enricher<List<PositionDetails>> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbPositionsEnricher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PositionDetails> enrich(List<PositionDetails> positionDetails) {
        for (PositionDetails positionDetail : positionDetails) {
            String sqlQuery = String.format("select * from instrument where ticker='%s'", positionDetail.getInstrumentDetails().getTicker());
            SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sqlQuery);

            while (resultSet.next()) {
                // Retrieve data from each row
                String securityType = resultSet.getString("security_type");
                double strike = resultSet.getDouble("strike");
                String maturity = resultSet.getString("maturity");
                LocalDate maturityDate = null;
                if(maturity != null) {
                    maturityDate = LocalDate.parse(maturity);
                }
                String tradingCurrency = resultSet.getString("trading_currency");
                String underlyingTicker = resultSet.getString("underlying_ticker");
                int lotSize = resultSet.getInt("lot_size");
                positionDetail.getInstrumentDetails().setLotSize(lotSize);
                positionDetail.getInstrumentDetails().setUnderlyingTicker(underlyingTicker);
                positionDetail.getInstrumentDetails().setMaturityDate(maturityDate);
                positionDetail.getInstrumentDetails().setStrikePrice(strike);
                positionDetail.getInstrumentDetails().setSecurityType(securityType);
                positionDetail.getInstrumentDetails().setTradingCurrency(tradingCurrency);
            }

        }
        return positionDetails;
    }
}
