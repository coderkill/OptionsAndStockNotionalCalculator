package notionalCalculator;

import POJO.MarketData;
import POJO.PositionDetails;

public class OptionsAndEquitiesCalculator implements NotionalCalculator {
    protected double unrealisedPnl;
    protected double averageEntryPrice;
    protected Double lastTradedPrice = 0d;
    protected PositionDetails positionDetails;
    protected double notional_local_currency;
    protected double notional_usd_currency;
    double lotSize = 1;
    @Override
    public void updateMetrics(MarketData marketData) {
//        boolean callOption =
//        double optionsPrice = OptionsPricingCalculator.calculate()
        unrealisedPnl = ((averageEntryPrice - lastTradedPrice) * lotSize * positionDetails.getPositionSize())/marketData.getFxRate();
        notional_local_currency = lastTradedPrice * lotSize * positionDetails.getPositionSize();
        notional_usd_currency = notional_local_currency / marketData.getFxRate();
    }

    @Override
    public double fetchLatestNotional() {
        return notional_usd_currency;
    }

}
