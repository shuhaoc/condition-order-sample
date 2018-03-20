package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public abstract class AbstractDelayConfirmCondition implements DelayConfirmCondition {
    final DelayConfirmCounter counter;
    final MarketCondition marketCondition;

    AbstractDelayConfirmCondition(int confirmTimes, MarketCondition marketCondition) {
        this.counter = new DelayConfirmCounter(confirmTimes);
        this.marketCondition = marketCondition;
    }

    AbstractDelayConfirmCondition(int confirmTimes, int confirmedCount, MarketCondition marketCondition) {
        this.counter = new DelayConfirmCounter(confirmTimes, confirmedCount);
        this.marketCondition = marketCondition;
    }

    public DelayConfirmCounter getCounter() {
        return counter;
    }

    @Override
    public int getConfirmedCount() {
        return counter.getConfirmedCount();
    }

    public MarketCondition getMarketCondition() {
        return marketCondition;
    }

    @Override
    public boolean isDirty() {
        return counter.isDirty();
    }

    @Override
    public void clearDirty() {
        counter.clearDirty();
    }

    @Override
    public void resetCounter() {
        counter.reset();
    }
}
