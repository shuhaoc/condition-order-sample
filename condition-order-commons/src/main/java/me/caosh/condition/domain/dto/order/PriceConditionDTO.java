package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.condorder.domain.orders.price.PriceConditionFacade;
import hbec.intellitrade.condorder.domain.orders.price.PriceConditionFacadeBuilder;
import me.caosh.autoasm.MappedClass;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@MappedClass(value = PriceConditionFacade.class, builderClass = PriceConditionFacadeBuilder.class)
public class PriceConditionDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    private Integer compareOperator;
    private BigDecimal targetPrice;
    private DelayConfirmDTO delayConfirm;
    private DeviationCtrlDTO deviationCtrl;
    private Integer delayConfirmedCount;

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

    public Integer getDelayConfirmedCount() {
        return delayConfirmedCount;
    }

    public void setDelayConfirmedCount(Integer delayConfirmedCount) {
        this.delayConfirmedCount = delayConfirmedCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceConditionDTO.class).omitNullValues()
                          .add("compareOperator", compareOperator)
                          .add("targetPrice", targetPrice)
                          .add("delayConfirm", delayConfirm)
                          .add("deviationCtrl", deviationCtrl)
                          .add("delayConfirmedCount", delayConfirmedCount)
                          .toString();
    }
}
