package positioninitializer;

import POJO.PositionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sqllite.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

@Component("positionsEnricher")
public class DbPositionsEnricher implements Enricher<List<PositionDetails>> {

    private SQLiteConnection connection;
    @Autowired
    public DbPositionsEnricher(SQLiteConnection connection) {
        this.connection = connection;
    }
    @Override
    public List<PositionDetails> enrich(List<PositionDetails> positionDetails) {
        for(PositionDetails positionDetail:  positionDetails) {
            try {
                Statement statement = connection.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(String.format("select * from instrument where ticker='%s'", positionDetail.getInstrumentDetails().getTicker()));
//                while(resultSet.next()) {
                String securityType = resultSet.getString("security_type");
                Double strike = resultSet.getDouble("strike");
                Date maturity = resultSet.getDate("maturity");
                String tradingCurrency = resultSet.getString("trading_currency");
                String underlyingTicker = resultSet.getString("underlying_ticker");
                Integer lotSize = resultSet.getInt("lot_size");

//                positionDetail.
//                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
