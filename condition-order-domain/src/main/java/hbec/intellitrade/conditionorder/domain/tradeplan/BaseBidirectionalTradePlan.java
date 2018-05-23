package hbec.intellitrade.conditionorder.domain.tradeplan;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.Buy;
import hbec.intellitrade.strategy.domain.signal.Sell;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;

import java.util.Objects;

/**
 * 双向交易基类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
public abstract class BaseBidirectionalTradePlan implements BidirectionalTradePlan {
    private final TradeNumber tradeNumber;

    BaseBidirectionalTradePlan(TradeNumber tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    @Override
    public EntrustCommand createEntrustCommand(TradeSignal tradeSignal,
            SecurityInfo securityInfo,
            TradingMarketSupplier tradingMarketSupplier) {
        if (tradeSignal instanceof Buy) {
            return getBuyPlan().createEntrustCommand(tradeSignal, securityInfo, tradingMarketSupplier);
        } else if (tradeSignal instanceof Sell) {
            return getSellPlan().createEntrustCommand(tradeSignal, securityInfo, tradingMarketSupplier);
        }
        throw new IllegalArgumentException(String.valueOf(tradeSignal));
    }


    @Override
    public TradeNumber getTradeNumber() {
        return tradeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        BaseBidirectionalTradePlan that = (BaseBidirectionalTradePlan) o;
        return Objects.equals(tradeNumber, that.tradeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeNumber);
    }
}
