package hbec.intellitrade.strategy.domain.condition;

import hbec.intellitrade.strategy.domain.condition.time.BasicTimeCondition;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.joda.time.LocalDateTime;

/**
 * 基于时间因子的骨架类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public abstract class AbstractBasicTimeCondition implements BasicTimeCondition {
    @Override
    public TradeSignal onTimeTick(LocalDateTime localDateTime) {
        boolean satisfiedAt = getTimeFactor().apply(localDateTime);
        if (satisfiedAt) {
            return Signals.buyOrSell();
        }
        return Signals.none();
    }
}
