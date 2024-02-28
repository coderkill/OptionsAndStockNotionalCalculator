package com.server.enrichers;

import com.server.POJO.PositionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(2)
@Component("fxEnricher")
public class FxEnricher implements Enricher<List<PositionDetails>> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FxEnricher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PositionDetails> enrich(List<PositionDetails> positionDetails) {
        for (PositionDetails positionDetail : positionDetails) {
            String sqlQuery = String.format("select * from fx_bar_timestamp where currency_from='%s' and timestamp_date='%s'", positionDetail.getInstrumentDetails().getTradingCurrency(), "2020-02-27");
            SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sqlQuery);
            while (resultSet.next()) {
                double fxRate = resultSet.getDouble("fx_rate");
                positionDetail.setFxRate(fxRate);
            }

        }
        return positionDetails;
    }
}
