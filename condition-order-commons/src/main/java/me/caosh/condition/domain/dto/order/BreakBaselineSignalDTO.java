package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.signal.BreakBaseline;
import me.caosh.autoasm.MappedClass;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/18
 */
@MappedClass(BreakBaseline.class)
public class BreakBaselineSignalDTO implements SignalDTO {
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BreakBaselineSignalDTO.class).omitNullValues()
                          .toString();
    }
}
