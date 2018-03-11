package hbec.intellitrade.strategy.domain;

import hbec.intellitrade.common.market.MarketID;

/**
 * 需要接收实时行情作为输入的策略
 * <p>
 * 实现该接口的不一定是行情驱动策略，比如时间条件单的实时输出也需要实时行情
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
public interface MarketTrackingStrategy extends Strategy {
    /**
     * 跟踪行情ID
     *
     * @return 跟踪行情ID
     */
    MarketID getTrackMarketID();
}
