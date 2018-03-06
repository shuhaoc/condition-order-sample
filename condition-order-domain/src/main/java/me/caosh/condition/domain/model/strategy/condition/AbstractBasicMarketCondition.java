package me.caosh.condition.domain.model.strategy.condition;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.market.PredictableMarketCondition;

import java.math.BigDecimal;

/**
 * 单一因子的行情条件，直接根据当前价判断是否触发
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public abstract class AbstractBasicMarketCondition implements PredictableMarketCondition {
    @Override
    public TradeSignal onMarketUpdate(RealTimeMarket realTimeMarket) {
        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        TargetPriceFactor targetPriceFactor = getTargetPriceFactor();
        boolean satisfied = targetPriceFactor.apply(currentPrice);
        if (satisfied) {
            return Signals.buyOrSell();
        }
        return Signals.none();
    }
}
