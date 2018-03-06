package me.caosh.condition.domain.model.order;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.strategy.domain.MarketDrivenStrategy;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/20
 */
public abstract class AbstractMarketConditionOrder extends AbstractConditionOrder implements MarketDrivenStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractMarketConditionOrder.class);

    public AbstractMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                        StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
    }

    public AbstractMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                        LocalDateTime expireTime, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, expireTime, strategyState);
    }

    @Override
    public MarketID getTrackMarketID() {
        return getSecurityInfo().getMarketID();
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        if (getStrategyState() != StrategyState.ACTIVE) {
            return Signals.none();
        }
        return getCondition().onMarketTick(realTimeMarket);
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        if (isMonitoringState() && isExpiredAt(localDateTime)) {
            return Signals.expire();
        }
        return Signals.none();
    }
}
