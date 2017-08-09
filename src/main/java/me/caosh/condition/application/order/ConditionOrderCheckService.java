package me.caosh.condition.application.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;

import java.util.List;

/**
 * Created by caosh on 2017/8/9.
 */
public interface ConditionOrderCheckService {
    void checkWithRealTimeMarkets(List<RealTimeMarket> realTimeMarketList);
}
