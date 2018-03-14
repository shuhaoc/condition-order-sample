package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.strategy.domain.MarketDrivenStrategy;
import hbec.intellitrade.strategy.domain.TimeDrivenStrategy;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/20
 */
public abstract class AbstractMarketConditionOrder extends AbstractConditionOrder implements MarketDrivenStrategy, TimeDrivenStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractMarketConditionOrder.class);

    private final MonitorTimeRange monitorTimeRange;

    public AbstractMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, OrderState orderState,
                                        SecurityInfo securityInfo, LocalDateTime expireTime) {
        super(orderId, tradeCustomerInfo, securityInfo, expireTime, orderState);
        this.monitorTimeRange = NoneMonitorTimeRange.INSTANCE;
    }

    public AbstractMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, OrderState orderState,
                                        SecurityInfo securityInfo, LocalDateTime expireTime, MonitorTimeRange monitorTimeRange) {
        super(orderId, tradeCustomerInfo, securityInfo, expireTime, orderState);
        this.monitorTimeRange = monitorTimeRange;
    }

    /**
     * 行情驱动策略的条件必然是行情条件
     *
     * @return 行情条件
     */
    protected abstract MarketCondition getCondition();

    @Override
    public MarketID getTrackMarketID() {
        return getSecurityInfo().getMarketID();
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        if (getOrderState() != OrderState.ACTIVE) {
            return Signals.none();
        }

        // TODO: 是否合适？？？
        if (!monitorTimeRange.isInRange(realTimeMarket.getMarketTime())) {
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
