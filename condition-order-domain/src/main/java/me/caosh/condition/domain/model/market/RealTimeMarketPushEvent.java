package me.caosh.condition.domain.model.market;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class RealTimeMarketPushEvent {
    private final Map<String, RealTimeMarket> marketMap;

    public RealTimeMarketPushEvent(Map<String, RealTimeMarket> marketMap) {
        this.marketMap = marketMap;
    }

    public Map<String, RealTimeMarket> getMarketMap() {
        return marketMap;
    }
}
