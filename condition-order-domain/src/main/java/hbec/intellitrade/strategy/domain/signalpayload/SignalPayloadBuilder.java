package hbec.intellitrade.strategy.domain.signalpayload;

import hbec.intellitrade.common.market.RealTimeMarketBuilder;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;
import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/7
 */
public class SignalPayloadBuilder implements ConvertibleBuilder<SignalPayload> {
    private Integer triggerId;
    private Signal signal;
    private ConditionOrder strategy;
    private RealTimeMarketBuilder realTimeMarket;

    public SignalPayloadBuilder setTriggerId(Integer triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public SignalPayloadBuilder setSignal(Signal signal) {
        this.signal = signal;
        return this;
    }

    public void setStrategy(ConditionOrder strategy) {
        this.strategy = strategy;
    }

    public SignalPayloadBuilder setRealTimeMarket(RealTimeMarketBuilder realTimeMarket) {
        this.realTimeMarket = realTimeMarket;
        return this;
    }

    @Override
    public SignalPayload build() {
        if (realTimeMarket != null) {
            return new MarketSignalPayload(triggerId, signal, strategy, realTimeMarket.build());
        }
        return new SignalPayload(triggerId, signal, strategy);
    }
}
