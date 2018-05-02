package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.conditionorder.domain.orders.DecoratedMarketCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactor;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
public class DecoratedGridSubCondition extends DecoratedMarketCondition<GridSubCondition> {
    public DecoratedGridSubCondition(GridSubCondition rawCondition,
            DelayConfirm delayConfirm,
            DeviationCtrl deviationCtrl,
            int delayConfirmedCount) {
        super(rawCondition, delayConfirm, deviationCtrl, delayConfirmedCount);
    }

    public BinaryTargetPriceFactor getMainFactor() {
        return getRawCondition().getMainFactor();
    }

    public BinaryTargetPriceFactor getTurnBackFactor() {
        return getRawCondition().getTurnBackFactor();
    }
}
