package me.caosh.condition.domain.model.strategy.condition.delayconfirm;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连续延迟确认
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class ContinuousDelayConfirmCondition implements MarketCondition {
    private static final Logger logger = LoggerFactory.getLogger(ContinuousDelayConfirmCondition.class);

    private final DelayConfirmCounter counter;
    private final MarketCondition marketCondition;

    public ContinuousDelayConfirmCondition(int confirmingCount, MarketCondition marketCondition) {
        this.counter = new DelayConfirmCounter(confirmingCount);
        this.marketCondition = marketCondition;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        TradeSignal tradeSignal = marketCondition.onMarketTick(realTimeMarket);
        if (!tradeSignal.isValid()) {
            counter.reset();
            logger.info("Confirmed count reset due to NONE signal");
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

        ContinuousDelayConfirmCondition that = (ContinuousDelayConfirmCondition) o;

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
        return MoreObjects.toStringHelper(ContinuousDelayConfirmCondition.class).omitNullValues()
                .add("counter", counter)
                .add("marketCondition", marketCondition)
                .toString();
    }
}
