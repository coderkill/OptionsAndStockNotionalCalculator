package com.server.notionalCalculator;

import com.server.POJO.InstrumentDetails;
import com.server.POJO.MarketData;
import com.server.POJO.NotionalData;
import com.server.POJO.PositionDetails;
import com.server.pricingcalculator.OptionsPricingCalculator;
import com.server.pricingcalculator.StockPricingCalculator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static java.lang.Thread.sleep;

public class OptionsAndEquitiesNotionalCalculator implements NotionalCalculator {
    protected double averageEntryPrice;
    protected Double lastTradedPrice = 0d;
    protected PositionDetails positionDetails;
    protected double notional;
    double lotSize;
    double fxRate;

    public OptionsAndEquitiesNotionalCalculator(PositionDetails positionDetails) {
        this.positionDetails = positionDetails;
        InstrumentDetails instrumentDetails = positionDetails.getInstrumentDetails();
        this.lotSize = instrumentDetails.getLotSize();
        this.lastTradedPrice = positionDetails.getStartPrice();
        this.averageEntryPrice = positionDetails.getStartPrice();
        this.fxRate = positionDetails.getFxRate();
        double priceForGenerator = this.lastTradedPrice;
        if(!"Stock".equals(instrumentDetails.getSecurityType())) {
            priceForGenerator = positionDetails.getUnderlyingPrice();
        }
        updateMetrics(new MarketData(instrumentDetails.getTicker(), this.lastTradedPrice));
        Thread thread = new Thread(new MarketDataGenerator(instrumentDetails.getTicker(), instrumentDetails.getMaturityDate(), priceForGenerator, instrumentDetails.getStrikePrice(), instrumentDetails.getSecurityType(), instrumentDetails.getUnderlyingTicker(), this));
        thread.start();
    }

    @Override
    public void updateMetrics(MarketData marketData) {
        if (!positionDetails.getInstrumentDetails().getTicker().equals(marketData.getTicker())) {
            return;
        }
        this.lastTradedPrice = marketData.getPrice();
//        double optionsPrice = OptionsPricingCalculator.calculate()
//        unrealisedPnl = ((averageEntryPrice - lastTradedPrice) * lotSize * positionDetails.getPositionSize())/marketData.getFxRate();
        notional = lastTradedPrice * lotSize * positionDetails.getPositionSize();
//        notional_usd_currency = notional_local_currency / marketData.getFxRate();
    }

    @Override
    public NotionalData fetchLatestNotional() {
        return new NotionalData(positionDetails.getInstrumentDetails().getTicker(), this.lastTradedPrice, (double) positionDetails.getPositionSize(), notional);
    }

    public class MarketDataGenerator implements Runnable {

        String ticker;
        String underlyingTicker;
        LocalDate maturityDate;
        double startingPrice;
        String securityType;
        double latestPrice;
        double drift = 0.05;          // Drift (average return)
        double volatility = 0.1;      // Volatility (standard deviation of return)
        double dt;              // Time step (in seconds)
        LocalDate currentDate = LocalDate.of(2020, 2, 27);
        double increment = 30;
        double strikePrice;
        OptionsAndEquitiesNotionalCalculator optionsAndEquitiesNotionalCalculator;

        public MarketDataGenerator(String ticker, LocalDate maturityDate, double startingPrice, double strikePrice, String securityType, String underlyingTicker, OptionsAndEquitiesNotionalCalculator optionsAndEquitiesNotionalCalculator) {
            this.ticker = ticker;
            if (maturityDate != null) {
                this.maturityDate = maturityDate;
            }
            this.startingPrice = startingPrice;
            this.latestPrice = startingPrice;
            this.securityType = securityType;
            this.strikePrice = strikePrice;
            this.underlyingTicker = underlyingTicker;
            this.dt = 0;
            this.optionsAndEquitiesNotionalCalculator = optionsAndEquitiesNotionalCalculator;
        }

        public double getLatestPrice() {
            return latestPrice;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    double price = StockPricingCalculator.calculate(latestPrice, drift, volatility, 1);
                    dt = dt + increment;
                    this.latestPrice = price;
                    if (!"Stock".equals(securityType)) {
                        double tInSeconds = (ChronoUnit.DAYS.between(currentDate, maturityDate) * 86400) - increment;
                        price = OptionsPricingCalculator.calculate("Call".equals(this.securityType), price, strikePrice, 0.05, tInSeconds / 31556952, volatility);
                    }
                    if (price >2) {
                        optionsAndEquitiesNotionalCalculator.updateMetrics(new MarketData(this.ticker, price));
                    } else {
                        Random random = new Random();
                        optionsAndEquitiesNotionalCalculator.updateMetrics(new MarketData(this.ticker, this.latestPrice - random.nextInt((20 - 10) + 1) + 10));
                    }
                    sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
