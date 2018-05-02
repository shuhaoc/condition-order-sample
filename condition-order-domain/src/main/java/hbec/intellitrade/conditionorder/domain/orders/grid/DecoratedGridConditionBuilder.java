package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmBuilder;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlBuilder;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.autoasm.ConvertibleBuilder;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
public class DecoratedGridConditionBuilder implements ConvertibleBuilder<DecoratedGridCondition> {
    private BigDecimal basePrice;
    private BinaryFactorType binaryFactorType;

    private GridSubConditionBuilder buyCondition = new GridSubConditionBuilder();
    private GridSubConditionBuilder sellCondition = new GridSubConditionBuilder();

    private boolean useGuaranteedPrice = false;
    private DelayConfirmBuilder delayConfirm = new DelayConfirmBuilder();
    private DeviationCtrlBuilder deviationCtrl = new DeviationCtrlBuilder();

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setBinaryFactorType(BinaryFactorType binaryFactorType) {
        this.binaryFactorType = binaryFactorType;
    }

    public GridSubConditionBuilder getBuyCondition() {
        return buyCondition;
    }

    public GridSubConditionBuilder getSellCondition() {
        return sellCondition;
    }

    public void setUseGuaranteedPrice(boolean useGuaranteedPrice) {
        this.useGuaranteedPrice = useGuaranteedPrice;
    }

    public void setDelayConfirm(DelayConfirmBuilder delayConfirm) {
        this.delayConfirm = delayConfirm;
    }

    public void setDeviationCtrl(DeviationCtrlBuilder deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
    }

    @Override
    public DecoratedGridCondition build() {
        return new DecoratedGridCondition(basePrice,
                binaryFactorType,
                buyCondition.build(basePrice, CompareOperator.LE, binaryFactorType, useGuaranteedPrice),
                sellCondition.build(basePrice, CompareOperator.GE, binaryFactorType, useGuaranteedPrice),
                useGuaranteedPrice,
                delayConfirm.build(),
                deviationCtrl.build(),
                0,
                0);
    }
}
