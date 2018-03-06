package me.caosh.condition.domain.model.strategy.condition.delayconfirm;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.market.MarketCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 累计延迟确认实现
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class AccumulatedDelayConfirmCondition implements MarketCondition {
    private static final Logger logger = LoggerFactory.getLogger(AccumulatedDelayConfirmCondition.class);

    private final DelayConfirmCounter counter;
    private final MarketCondition marketCondition;

    public AccumulatedDelayConfirmCondition(int confirmingCount, MarketCondition marketCondition) {
        this.counter = new DelayConfirmCounter(confirmingCount);
        this.marketCondition = marketCondition;
    }

    @Override
    public TradeSignal onMarketUpdate(RealTimeMarket realTimeMarket) {
        TradeSignal tradeSignal = marketCondition.onMarketUpdate(realTimeMarket);
        if (!tradeSignal.isValid()) {
            return tradeSignal;
        }

        counter.increaseConfirmedCount();
        if (counter.isConfirmCompleted()) {
            logger.info("Confirmed count is enough, counter={}", counter);
            return tradeSignal;
        } else {
            logger.info("Delay confirm, counter={}", counter);
            return Signals.none();
        }
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        marketCondition.accept(visitor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccumulatedDelayConfirmCondition that = (AccumulatedDelayConfirmCondition) o;

        if (!counter.equals(that.counter)) {
            return false;
        }
        return marketCondition.equals(that.marketCondition);
    }

    @Override
    public int hashCode() {
        int result = counter.hashCode();
        result = 31 * result + marketCondition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(AccumulatedDelayConfirmCondition.class).omitNullValues()
                .add("counter", counter)
                .add("marketCondition", marketCondition)
                .toString();
    }
}
