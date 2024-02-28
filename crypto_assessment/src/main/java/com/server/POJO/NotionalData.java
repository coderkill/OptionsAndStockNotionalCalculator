package com.server.POJO;

public class NotionalData {
    private String ticker;
    private Double price;
    private Double quantity;
    private Double notional;

    public String getTicker() {
        return ticker;
    }

    public Double getPrice() {
        return price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getNotional() {
        return notional;
    }

    public NotionalData() {
    }

    public NotionalData(String ticker, Double price, Double quantity, Double notional) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
        this.notional = notional;
    }

    public NotionalData(String ticker, Double notional) {
        this.ticker = ticker;
        this.notional = notional;
    }

    @Override
    public String toString() {
        return "NotionalData{" +
                "ticker='" + ticker + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", notional=" + notional +
                '}';
    }
}
