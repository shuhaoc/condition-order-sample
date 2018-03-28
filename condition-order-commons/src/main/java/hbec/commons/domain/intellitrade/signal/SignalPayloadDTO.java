package hbec.commons.domain.intellitrade.signal;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.market.RealTimeMarketDTO;
import hbec.commons.domain.intellitrade.condorder.ConditionOrderDTO;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 */
public class SignalPayloadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer triggerId;
    private SignalDTO signal;
    private ConditionOrderDTO strategy;
    private RealTimeMarketDTO realTimeMarket;

    public Integer getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(Integer triggerId) {
        this.triggerId = triggerId;
    }

    public SignalDTO getSignal() {
        return signal;
    }

    public void setSignal(SignalDTO signal) {
        this.signal = signal;
    }

    public ConditionOrderDTO getStrategy() {
        return strategy;
    }

    public void setStrategy(ConditionOrderDTO strategy) {
        this.strategy = strategy;
    }

    public RealTimeMarketDTO getRealTimeMarket() {
        return realTimeMarket;
    }

    public void setRealTimeMarket(RealTimeMarketDTO realTimeMarket) {
        this.realTimeMarket = realTimeMarket;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SignalPayloadDTO.class).omitNullValues()
                .add("triggerId", triggerId)
                .add("signal", signal)
                .add("strategy", strategy)
                .add("realTimeMarket", realTimeMarket)
                .toString();
    }
}
