package hbec.intellitrade.strategy.domain.signalpayload;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.Strategy;
import hbec.intellitrade.strategy.domain.signal.Signal;
import org.joda.time.LocalTime;

/**
 * 行情触发的信号负载
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class MarketSignalPayload extends SignalPayload {
    private final RealTimeMarket realTimeMarket;

    public MarketSignalPayload(Signal signal, Strategy strategy, RealTimeMarket realTimeMarket) {
        super(SignalPayloads.triggerId((int) strategy.getStrategyId(), realTimeMarket.getMarketTime().toLocalTime()), signal, strategy);
        this.realTimeMarket = realTimeMarket;
    }

    public MarketSignalPayload(int triggerId, Signal signal, Strategy strategy, RealTimeMarket realTimeMarket) {
        super(triggerId, signal, strategy);
        this.realTimeMarket = realTimeMarket;
    }

    public RealTimeMarket getRealTimeMarket() {
        return realTimeMarket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        MarketSignalPayload that = (MarketSignalPayload) o;

        return realTimeMarket.equals(that.realTimeMarket);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + realTimeMarket.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(MarketSignalPayload.class).omitNullValues()
                .add("triggerId", getTriggerId())
                .add("signal", getSignal())
                .add("strategy", getStrategy())
                .add("realTimeMarket", realTimeMarket)
                .toString();
    }
}
