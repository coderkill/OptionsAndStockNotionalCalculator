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

@Order(3)
@Component("priceEnricher")
public class PriceEnricher implements Enricher<List<PositionDetails>> {

    private final SQLiteConnection connection;

    @Autowired
    public PriceEnricher(SQLiteConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<PositionDetails> enrich(List<PositionDetails> positionDetails) {
        try {
            Statement statement = connection.getConnection().createStatement();
            for (PositionDetails positionDetail : positionDetails) {
                ResultSet resultSet = statement.executeQuery(String.format("select * from price_bar_timestamp where ticker='%s' and trading_date='%s'", positionDetail.getInstrumentDetails().getTicker(), "2024-02-27"));
                Double price = resultSet.getDouble("price");
                positionDetail.setStartPrice(price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
