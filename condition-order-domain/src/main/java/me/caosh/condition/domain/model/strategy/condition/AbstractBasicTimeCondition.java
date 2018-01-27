package me.caosh.condition.domain.model.strategy.condition;

import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.time.BasicTimeCondition;
import org.joda.time.LocalDateTime;

/**
 * 基于时间因子的骨架类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public abstract class AbstractBasicTimeCondition implements BasicTimeCondition {
    @Override
    public TradeSignal onSecondTick() {
        boolean satisfiedNow = getTimeFactor().apply(LocalDateTime.now());
        if (satisfiedNow) {
            return Signals.buyOrSell();
        }
        return Signals.none();
    }
}
