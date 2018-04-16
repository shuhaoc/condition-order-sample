package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 累计延迟确认实现
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class AccumulatedDelayConfirmCondition extends AbstractDelayConfirmCondition implements MarketCondition {
    private static final Logger logger = LoggerFactory.getLogger(AccumulatedDelayConfirmCondition.class);

    public AccumulatedDelayConfirmCondition(int confirmTimes, MarketCondition marketCondition) {
        super(confirmTimes, marketCondition);
    }

    public AccumulatedDelayConfirmCondition(int confirmTimes, int confirmedCount, MarketCondition marketCondition) {
        super(confirmTimes, confirmedCount, marketCondition);
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        TradeSignal tradeSignal = marketCondition.onMarketTick(realTimeMarket);
        if (!tradeSignal.isValid()) {
            // 未触发交易信号时，检查动态属性是否变化
            boolean isDynamic = marketCondition instanceof DynamicCondition;
            if (isDynamic) {
                boolean hasDirty = ((DynamicCondition) marketCondition).isDirty();
                if (hasDirty) {
                    counter.reset();
                }
            }

            return tradeSignal;
        }

        counter.increaseConfirmedCount();
        if (counter.isConfirmCompleted()) {
            logger.trace("Confirmed count is enough, counter={}", counter);
            return tradeSignal;
        } else {
            logger.trace("Delay confirm, counter={}", counter);
            return Signals.none();
        }
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
