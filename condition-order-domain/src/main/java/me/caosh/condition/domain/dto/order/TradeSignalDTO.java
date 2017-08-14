package me.caosh.condition.domain.dto.order;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public interface TradeSignalDTO extends Serializable {
    void accept(TradeSignalDTOVisitor visitor);
}
