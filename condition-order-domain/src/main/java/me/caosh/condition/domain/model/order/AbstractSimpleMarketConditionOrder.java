package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import org.joda.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class AbstractSimpleMarketConditionOrder extends AbstractMarketConditionOrder {
    private final BasicTradePlan tradePlan;

    public AbstractSimpleMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                              BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
        this.tradePlan = tradePlan;
    }

    public AbstractSimpleMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                              LocalDateTime expireTime, BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, expireTime, strategyState);
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
