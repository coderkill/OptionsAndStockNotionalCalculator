package com.server.notionalCalculator;

import com.server.POJO.MarketData;
import com.server.POJO.NotionalData;

public interface NotionalCalculator {
    void updateMetrics(MarketData marketData);

    NotionalData fetchLatestNotional();

}
