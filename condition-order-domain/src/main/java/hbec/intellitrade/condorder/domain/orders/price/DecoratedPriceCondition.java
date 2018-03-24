package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.condorder.domain.orders.DecoratedMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;

import java.math.BigDecimal;

/**
 * 价格条件整体总装类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/20
 */
public class DecoratedPriceCondition extends DecoratedMarketCondition<PriceCondition> implements DynamicCondition {
    /**
     * 延迟确认参数
     */
    private final DelayConfirm delayConfirm;

    /**
     * 偏差控制参数
     */
    private final DeviationCtrl deviationCtrl;

    /**
     * 构造价格总装类
     *
     * @param priceCondition      原始价格条件
     * @param delayConfirm        延迟确认参数
     * @param deviationCtrl       偏差控制参数
     * @param delayConfirmedCount 当前价格条件的延迟确认次数
     */
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
