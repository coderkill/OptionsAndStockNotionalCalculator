package positioninitializer.enrichers;

import POJO.PositionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sqllite.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Order(2)
@Component("fxEnricher")
public class FxEnricher implements Enricher<List<PositionDetails>> {

    private final SQLiteConnection connection;

    @Autowired
    public FxEnricher(SQLiteConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<PositionDetails> enrich(List<PositionDetails> positionDetails) {
        try {
            Statement statement = connection.getConnection().createStatement();
            for (PositionDetails positionDetail : positionDetails) {
                ResultSet resultSet = statement.executeQuery(String.format("select * from fx_bar_timestamp where currency_to='%s' and trading_date='%s'", positionDetail.getInstrumentDetails().getTradingCurrency(), "2024-02-27"));
                Double fxRate = resultSet.getDouble("fx_rate");
                positionDetail.setFxRate(fxRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
