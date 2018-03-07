package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.signal.Expire;
import me.caosh.autoasm.MappedClass;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/7
 */
@MappedClass(Expire.class)
public class ExpireSignalDTO implements SignalDTO {
    @Override
    public void accept(TradeSignalDTOVisitor visitor) {

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ExpireSignalDTO.class).omitNullValues()
                .toString();
    }
}
