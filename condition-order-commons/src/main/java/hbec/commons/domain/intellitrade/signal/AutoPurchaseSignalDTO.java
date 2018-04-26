package hbec.commons.domain.intellitrade.signal;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.signal.AutoPurchase;
import me.caosh.autoasm.MappedClass;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
@MappedClass(AutoPurchase.class)
public class AutoPurchaseSignalDTO implements SignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(AutoPurchaseSignalDTO.class).omitNullValues()
                .toString();
    }
}
