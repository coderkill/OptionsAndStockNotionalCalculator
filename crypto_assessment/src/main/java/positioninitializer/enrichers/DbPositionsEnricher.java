package positioninitializer.enrichers;

import POJO.PositionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sqllite.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

@Order(1)
@Component("dbPositionEnricher")
public class DbPositionsEnricher implements Enricher<List<PositionDetails>> {

    private final SQLiteConnection connection;

    @Autowired
    public DbPositionsEnricher(SQLiteConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<PositionDetails> enrich(List<PositionDetails> positionDetails) {
        try {
            Statement statement = connection.getConnection().createStatement();
            for (PositionDetails positionDetail : positionDetails) {
                ResultSet resultSet = statement.executeQuery(String.format("select * from instrument where ticker='%s'", positionDetail.getInstrumentDetails().getTicker()));
                String securityType = resultSet.getString("security_type");
                Double strike = resultSet.getDouble("strike");
                Date maturity = resultSet.getDate("maturity");
                String tradingCurrency = resultSet.getString("trading_currency");
                String underlyingTicker = resultSet.getString("underlying_ticker");
                Integer lotSize = resultSet.getInt("lot_size");
                positionDetail.getInstrumentDetails().setLotSize(lotSize);
                positionDetail.getInstrumentDetails().setUnderlyingTicker(underlyingTicker);
                positionDetail.getInstrumentDetails().setMaturityDate(maturity);
                positionDetail.getInstrumentDetails().setStrikePrice(strike);
                positionDetail.getInstrumentDetails().setSecurityType(securityType);
                positionDetail.getInstrumentDetails().setTradingCurrency(tradingCurrency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
