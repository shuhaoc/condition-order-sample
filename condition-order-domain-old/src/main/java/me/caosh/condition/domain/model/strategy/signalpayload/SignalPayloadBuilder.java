package me.caosh.condition.domain.model.strategy.signalpayload;

import hbec.intellitrade.common.market.RealTimeMarketBuilder;
import me.caosh.condition.domain.model.order.ConditionOrderBuilder;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signalpayload.MarketSignalPayload;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/7
 */
public class SignalPayloadBuilder implements ConvertibleBuilder<SignalPayload> {
    private Integer triggerId;
    private Signal signal;
    private ConditionOrderBuilder strategy;
    private RealTimeMarketBuilder realTimeMarket;

    public SignalPayloadBuilder setTriggerId(Integer triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public SignalPayloadBuilder setSignal(Signal signal) {
        this.signal = signal;
        return this;
    }

    public SignalPayloadBuilder setStrategy(ConditionOrderBuilder strategy) {
        this.strategy = strategy;
        return this;
    }

    public SignalPayloadBuilder setRealTimeMarket(RealTimeMarketBuilder realTimeMarket) {
        this.realTimeMarket = realTimeMarket;
        return this;
    }

    @Override
    public SignalPayload build() {
        if (realTimeMarket != null) {
            return new MarketSignalPayload(triggerId, signal, strategy.build(), realTimeMarket.build());
        }
        return new SignalPayload(triggerId, signal, strategy.build());
    }
}
