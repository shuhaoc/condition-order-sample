package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.signal.CrossBaseline;
import me.caosh.autoasm.MappedClass;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/18
 */
@MappedClass(CrossBaseline.class)
public class CrossBaselineSignalDTO implements SignalDTO {
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(CrossBaselineSignalDTO.class).omitNullValues()
                          .toString();
    }
}
