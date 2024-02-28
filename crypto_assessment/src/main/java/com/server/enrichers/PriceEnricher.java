package com.server.enrichers;

import com.server.POJO.PositionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(3)
@Component("priceEnricher")
public class PriceEnricher implements Enricher<List<PositionDetails>> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PriceEnricher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PositionDetails> enrich(List<PositionDetails> positionDetails) {
        for (PositionDetails positionDetail : positionDetails) {
            String sqlQuery = String.format("select * from price_bar_timestamp where ticker='%s' and timestamp_date='%s'", positionDetail.getInstrumentDetails().getTicker(), "2020-02-27");
            SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sqlQuery);
            while (resultSet.next()) {
                double price = resultSet.getDouble("price");
                positionDetail.setStartPrice(price);
            }
            if(!"Stock".equals(positionDetail.getInstrumentDetails().getSecurityType())) {
                String sqlQuery2 = String.format("select * from price_bar_timestamp where ticker='%s' and timestamp_date='%s'", positionDetail.getInstrumentDetails().getUnderlyingTicker(), "2020-02-27");
                SqlRowSet resultSet2 = jdbcTemplate.queryForRowSet(sqlQuery2);
                while (resultSet2.next()) {
                    double price = resultSet2.getDouble("price");
                    positionDetail.setUnderlyingPrice(price);
                }
            }
        }
        return positionDetails;
    }
}
