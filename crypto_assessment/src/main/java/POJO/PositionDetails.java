package POJO;

public class PositionDetails {
    InstrumentDetails instrumentDetails;
    int positionSize;
    double startPrice;
    String tradingCurrency;
    float fxRate;

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

    public String getTradingCurrency() {
        return tradingCurrency;
    }

    public float getFxRate() {
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

    public void setFxRate(float fxRate) {
        this.fxRate = fxRate;
    }
}
