package hbec.commons.domain.intellitrade.condorder;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/16
 */
@Convertible
public class DeviationCtrlDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Range(min = 0, max = 1)
    private Integer option;
    @DecimalMin("0.01")
    @DecimalMax("100")
    private BigDecimal limitPercent;

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public BigDecimal getLimitPercent() {
        return limitPercent;
    }

    public void setLimitPercent(BigDecimal limitPercent) {
        this.limitPercent = limitPercent;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DeviationCtrlDTO.class).omitNullValues()
                          .add("option", option)
                          .add("limitPercent", limitPercent)
                          .toString();
    }
}
