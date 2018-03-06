package hbec.intellitrade.strategy.domain.condition;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;

import java.math.BigDecimal;

/**
 * 单一因子的行情条件，直接根据当前价判断是否触发
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public abstract class AbstractBasicMarketCondition implements PredictableMarketCondition {
    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        TargetPriceFactor targetPriceFactor = getTargetPriceFactor();
        boolean satisfied = targetPriceFactor.apply(currentPrice);
        if (satisfied) {
            return Signals.buyOrSell();
        }
        return Signals.none();
    }
}
