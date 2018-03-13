package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import org.joda.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class AbstractSimpleMarketConditionOrder extends AbstractMarketConditionOrder {
    private final BasicTradePlan tradePlan;

    public AbstractSimpleMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                              LocalDateTime expireTime, BasicTradePlan tradePlan, OrderState orderState) {
        super(orderId, tradeCustomerInfo, orderState, securityInfo, expireTime);
        this.tradePlan = tradePlan;
    }

    public AbstractSimpleMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, OrderState orderState,
                                              SecurityInfo securityInfo, LocalDateTime expireTime, BasicTradePlan tradePlan,
                                              MonitorTimeRange monitorTimeRange) {
        super(orderId, tradeCustomerInfo, orderState, securityInfo, expireTime, monitorTimeRange);
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
