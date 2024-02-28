package POJO;

public class NotionalData {
    private String ticker;
    private Double price;
    private Double quantity;
    private Double notional;

    public NotionalData(String ticker, Double price, Double quantity, Double notional) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
        this.notional = notional;
    }
}
