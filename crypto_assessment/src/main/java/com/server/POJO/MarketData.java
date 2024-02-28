package com.server.POJO;

public class MarketData {
    String ticker;
    double price;
    double fxRate;

    public MarketData(String ticker, double price) {
        this.ticker = ticker;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public double getFxRate() {
        return fxRate;
    }
}
