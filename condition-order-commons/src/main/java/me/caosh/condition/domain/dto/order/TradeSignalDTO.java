package me.caosh.condition.domain.dto.order;

import me.caosh.autoasm.RuntimeType;
import hbec.intellitrade.strategy.domain.signal.Signal;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@RuntimeType(Signal.class)
public interface TradeSignalDTO extends Serializable {
    void accept(TradeSignalDTOVisitor visitor);
}
