package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.signal.BS;
import hbec.intellitrade.strategy.domain.signal.BsBuilder;
import me.caosh.autoasm.MappedClass;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@MappedClass(value = BS.class, builderClass = BsBuilder.class)
public class BsSignalDTO implements SignalDTO {
    private static final long serialVersionUID = 1L;

    private Boolean deviationExceeded;

    public Boolean getDeviationExceeded() {
        return deviationExceeded;
    }

    public void setDeviationExceeded(Boolean deviationExceeded) {
        this.deviationExceeded = deviationExceeded;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BsSignalDTO.class).omitNullValues()
                          .add("deviationExceeded", deviationExceeded)
                          .toString();
    }
}
