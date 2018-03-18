package hbec.intellitrade.strategy.domain.condition.deviation;

import com.google.common.base.Preconditions;
import me.caosh.autoasm.ConvertibleBuilder;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/16
 */
public class DeviationCtrlBuilder implements ConvertibleBuilder<DeviationCtrl> {
    private DeviationCtrlOption option = DeviationCtrlOption.DISABLED;
    private BigDecimal limitPercent;

    public void setOption(DeviationCtrlOption option) {
        this.option = option;
    }

    public void setLimitPercent(BigDecimal limitPercent) {
        this.limitPercent = limitPercent;
    }

    @Override
    public DeviationCtrl build() {
        if (option == DeviationCtrlOption.DISABLED) {
            return DisabledDeviationCtrl.DISABLED;
        }

        Preconditions.checkNotNull(limitPercent, "limitPercent cannot be null");
        return new DeviationCtrlInfo(limitPercent);
    }
}
