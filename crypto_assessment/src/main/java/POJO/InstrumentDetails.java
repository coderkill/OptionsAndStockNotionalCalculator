package POJO;

public class InstrumentDetails {
    String ticker;
    String tradingCurrency;
    String securityType;
    String maturityDate;
    float strikePrice;
    String underlyingTicker;
    int lotSize=1;

    public InstrumentDetails(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public String getTradingCurrency() {
        return tradingCurrency;
    }

    public void setTradingCurrency(String tradingCurrency) {
        this.tradingCurrency = tradingCurrency;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public float getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(float strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getUnderlyingTicker() {
        return underlyingTicker;
    }

    public void setUnderlyingTicker(String underlyingTicker) {
        this.underlyingTicker = underlyingTicker;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }
}
