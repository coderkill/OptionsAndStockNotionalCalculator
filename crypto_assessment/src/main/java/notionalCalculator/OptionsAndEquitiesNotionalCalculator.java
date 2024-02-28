package notionalCalculator;

import POJO.MarketData;
import POJO.PositionDetails;
import pricingcalculator.OptionsPricingCalculator;
import pricingcalculator.StockPricingCalculator;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class OptionsAndEquitiesNotionalCalculator implements NotionalCalculator {
    protected double unrealisedPnl;
    protected double averageEntryPrice;
    protected Double lastTradedPrice = 0d;
    protected PositionDetails positionDetails;
    protected double notional_local_currency;
    protected double notional_usd_currency;
    double lotSize = 1;
    double fxRate;

    public OptionsAndEquitiesNotionalCalculator(PositionDetails positionDetails){
        this.positionDetails = positionDetails;
        this.lotSize = positionDetails.getInstrumentDetails().getLotSize();
        this.lastTradedPrice = positionDetails.getStartPrice();
        this.averageEntryPrice = positionDetails.getStartPrice();
        this.fxRate = positionDetails.getFxRate();
    }

    @Override
    public void updateMetrics(MarketData marketData) {
//        this.positionDetails.
//        boolean callOption =
//        double optionsPrice = OptionsPricingCalculator.calculate()
//        unrealisedPnl = ((averageEntryPrice - lastTradedPrice) * lotSize * positionDetails.getPositionSize())/marketData.getFxRate();
        notional_local_currency = lastTradedPrice * lotSize * positionDetails.getPositionSize();
//        notional_usd_currency = notional_local_currency / marketData.getFxRate();
    }

    @Override
    public double fetchLatestNotional() {
        return notional_local_currency;
    }

    public class MarketDataGenerator implements Runnable  {

        String ticker;
        String underlyingTicker;
        Date maturityDate;
        double startingPrice;
        String securityType;
        double latestPrice;
        double drift = 0.05;          // Drift (average return)
        double volatility = 0.2;      // Volatility (standard deviation of return)
        double dt = 0.00001157407;              // Time step (in days)
        LocalDate currentDate = LocalDate.of(2020, 2, 27);
        double increment=1;
        double strikePrice;

        public MarketDataGenerator(String ticker, Date maturityDate, double startingPrice, double strikePrice,
                          String securityType, String underlyingTicker) {
            this.ticker = ticker;
            this.maturityDate = maturityDate;
            this.startingPrice = startingPrice;
            this.latestPrice = startingPrice;
            this.securityType = securityType;
            this.strikePrice = strikePrice;
            this.underlyingTicker = underlyingTicker;
            if("Stock".equals(securityType)) {
                this.dt = 0;
            }
        }

        public double getLatestPrice() {
            return latestPrice;
        }

        @Override
        public void run() {
            while (true) {
                double price = StockPricingCalculator.calculate(latestPrice, drift, volatility, dt+increment);
                dt = dt+increment;
                if ("Stock".equals(securityType)) {
                    this.latestPrice = price;
                } else {
//                    this.latestPrice = OptionsPricingCalculator.calculate("Call".equals(this.securityType),
//                            price, strikePrice)
                }
            }
        }
    }
}
