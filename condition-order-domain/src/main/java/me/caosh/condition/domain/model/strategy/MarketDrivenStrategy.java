package me.caosh.condition.domain.model.strategy;

import me.caosh.condition.domain.model.strategy.condition.market.MarketCondition;

/**
 * 行情驱动策略
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public interface MarketDrivenStrategy extends RealTimeMarketAware {
    /**
     * 行情驱动策略的条件必然是行情条件
     *
     * @return 行情条件
     */
    @Override
    MarketCondition getCondition();
}
