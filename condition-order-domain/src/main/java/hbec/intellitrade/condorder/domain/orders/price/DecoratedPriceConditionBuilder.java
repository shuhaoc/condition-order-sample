package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.condorder.domain.delayconfirm.count.DelayConfirmCount;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmBuilder;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlBuilder;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.autoasm.ConvertibleBuilder;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class DecoratedPriceConditionBuilder implements ConvertibleBuilder<DecoratedPriceCondition> {
    private CompareOperator compareOperator;
    private BigDecimal targetPrice;
    private DelayConfirmBuilder delayConfirm = new DelayConfirmBuilder();
    private DelayConfirmCount delayConfirmCount;
    private DeviationCtrlBuilder deviationCtrl = new DeviationCtrlBuilder();

    public DecoratedPriceConditionBuilder setCompareOperator(CompareOperator compareOperator) {
        this.compareOperator = compareOperator;
        return this;
    }

    public DecoratedPriceConditionBuilder setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
        return this;
    }

    public DelayConfirmBuilder getDelayConfirm() {
        return delayConfirm;
    }

    public DecoratedPriceConditionBuilder setDelayConfirm(DelayConfirmBuilder delayConfirm) {
        this.delayConfirm = delayConfirm;
        return this;
    }

    public DelayConfirmCount getDelayConfirmCount() {
        return delayConfirmCount;
    }

    public DecoratedPriceConditionBuilder setDelayConfirmCount(DelayConfirmCount delayConfirmCount) {
        this.delayConfirmCount = delayConfirmCount;
        return this;
    }

    public DeviationCtrlBuilder getDeviationCtrl() {
        return deviationCtrl;
    }

    public DecoratedPriceConditionBuilder setDeviationCtrl(DeviationCtrlBuilder deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
        return this;
    }

    @Override
    public DecoratedPriceCondition build() {
        return new DecoratedPriceCondition(new PriceCondition(compareOperator, targetPrice),
                                           delayConfirm.build(),
                                           (SingleDelayConfirmCount) delayConfirmCount,
                                           deviationCtrl.build());
    }
}