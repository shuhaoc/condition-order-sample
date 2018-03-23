package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.condorder.domain.orders.DecoratedMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;

import java.math.BigDecimal;

/**
 * 价格条件包装类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/20
 */
public class DecoratedPriceCondition extends DecoratedMarketCondition<PriceCondition> implements DynamicCondition {
    protected final DelayConfirm delayConfirm;
    protected final DeviationCtrl deviationCtrl;

    public DecoratedPriceCondition(PriceCondition priceCondition,
                                   DelayConfirm delayConfirm,
                                   DeviationCtrl deviationCtrl,
                                   int delayConfirmedCount) {
        super(priceCondition, delayConfirm, deviationCtrl, delayConfirmedCount);
        this.delayConfirm = delayConfirm;
        this.deviationCtrl = deviationCtrl;
    }

    public CompareOperator getCompareOperator() {
        return getRawCondition().getCompareOperator();
    }

    public BigDecimal getTargetPrice() {
        return getRawCondition().getTargetPrice();
    }

    public DelayConfirm getDelayConfirm() {
        return delayConfirm;
    }

    public DeviationCtrl getDeviationCtrl() {
        return deviationCtrl;
    }
}
