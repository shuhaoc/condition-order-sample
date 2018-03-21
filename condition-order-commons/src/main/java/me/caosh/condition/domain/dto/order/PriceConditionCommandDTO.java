package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.condorder.domain.orders.price.PriceConditionFacade;
import hbec.intellitrade.condorder.domain.orders.price.PriceConditionFacadeBuilder;
import me.caosh.autoasm.MappedClass;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@MappedClass(value = PriceConditionFacade.class, builderClass = PriceConditionFacadeBuilder.class)
public class PriceConditionCommandDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Range(min = 0, max = 1)
    private Integer compareOperator;
    @NotNull
    @DecimalMin("0")
    private BigDecimal targetPrice;
    @Valid
    private DelayConfirmDTO delayConfirm;
    @Valid
    private DeviationCtrlDTO deviationCtrl;

    public Integer getCompareOperator() {
        return compareOperator;
    }

    public void setCompareOperator(Integer compareOperator) {
        this.compareOperator = compareOperator;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public DelayConfirmDTO getDelayConfirm() {
        return delayConfirm;
    }

    public void setDelayConfirm(DelayConfirmDTO delayConfirm) {
        this.delayConfirm = delayConfirm;
    }

    public DeviationCtrlDTO getDeviationCtrl() {
        return deviationCtrl;
    }

    public void setDeviationCtrl(DeviationCtrlDTO deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceConditionCommandDTO.class).omitNullValues()
                          .add("compareOperator", compareOperator)
                          .add("targetPrice", targetPrice)
                          .add("delayConfirm", delayConfirm)
                          .add("deviationCtrl", deviationCtrl)
                          .toString();
    }
}
