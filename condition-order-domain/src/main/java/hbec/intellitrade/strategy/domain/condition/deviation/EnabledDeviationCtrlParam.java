package hbec.intellitrade.strategy.domain.condition.deviation;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/16
 */
public class EnabledDeviationCtrlParam implements DeviationCtrlParam {
    private final BigDecimal limitPercent;

    public EnabledDeviationCtrlParam(BigDecimal limitPercent) {
        Preconditions.checkArgument(limitPercent.compareTo(BigDecimal.ZERO) > 0,
                                    "limitPercent should be greater than 0");
        this.limitPercent = limitPercent;
    }

    @Override
    public DeviationCtrlOption getOption() {
        return DeviationCtrlOption.ENABLED;
    }

    public BigDecimal getLimitPercent() {
        return limitPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnabledDeviationCtrlParam that = (EnabledDeviationCtrlParam) o;

        return limitPercent.equals(that.limitPercent);
    }

    @Override
    public int hashCode() {
        return limitPercent.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EnabledDeviationCtrlParam.class).omitNullValues()
                          .add("limitPercent", limitPercent)
                          .add("option", getOption())
                          .toString();
    }
}
