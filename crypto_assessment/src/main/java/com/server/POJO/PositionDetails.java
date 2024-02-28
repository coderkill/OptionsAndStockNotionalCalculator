package com.server.POJO;

public class PositionDetails {
    InstrumentDetails instrumentDetails;
    int positionSize;
    double startPrice;
    double underlyingPrice;
    String tradingCurrency;
    double fxRate;

    public PositionDetails(InstrumentDetails instrumentDetails, int positionSize) {
        this.instrumentDetails = instrumentDetails;
        this.positionSize = positionSize;
    }

    public InstrumentDetails getInstrumentDetails() {
        return instrumentDetails;
    }

    public int getPositionSize() {
        return positionSize;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public double getUnderlyingPrice() {
        return underlyingPrice;
    }

    public void setUnderlyingPrice(double underlyingPrice) {
        this.underlyingPrice = underlyingPrice;
    }

    public String getTradingCurrency() {
        return tradingCurrency;
    }

    public double getFxRate() {
        return fxRate;
    }

    public void setPositionSize(int positionSize) {
        this.positionSize = positionSize;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public void setTradingCurrency(String tradingCurrency) {
        this.tradingCurrency = tradingCurrency;
    }

    public void setFxRate(double fxRate) {
        this.fxRate = fxRate;
    }
}
