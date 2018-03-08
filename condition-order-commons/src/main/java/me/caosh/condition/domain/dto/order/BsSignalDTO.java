package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.MappedClass;
import hbec.intellitrade.strategy.domain.signal.BS;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@MappedClass(BS.class)
public class BsSignalDTO implements SignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BsSignalDTO.class).omitNullValues()
                .toString();
    }
}
