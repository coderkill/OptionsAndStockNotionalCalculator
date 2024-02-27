package notionalCalculator;

import POJO.MarketData;

public interface NotionalCalculator {
    public void updateMetrics(MarketData marketData);

    public double fetchLatestNotional();
}
