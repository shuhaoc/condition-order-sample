package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndex;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import org.joda.time.LocalDateTime;

/**
 * 简单行情条件单，指交易计划为{@link BasicTradePlan}的一次性触发的条件单
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/20
 */
public abstract class AbstractSimpleMarketConditionOrder extends AbstractMarketConditionOrder {
    private final BasicTradePlan tradePlan;

    public AbstractSimpleMarketConditionOrder(Long orderId,
                                              TradeCustomerInfo tradeCustomerInfo,
                                              OrderState orderState,
                                              SecurityInfo securityInfo,
                                              TrackedIndex trackedIndexInfo,
                                              LocalDateTime expireTime,
                                              MonitorTimeRange monitorTimeRange,
                                              BasicTradePlan tradePlan) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              expireTime,
              trackedIndexInfo,
              monitorTimeRange
        );
        this.tradePlan = tradePlan;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        AbstractSimpleMarketConditionOrder that = (AbstractSimpleMarketConditionOrder) o;

        return tradePlan.equals(that.tradePlan);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + tradePlan.hashCode();
        return result;
    }
}
