package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmBuilder;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlBuilder;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.autoasm.ConvertibleBuilder;

import java.math.BigDecimal;

/**
 * @author caoshuhao@touker.com
 * @date 2018/4/14
 */
public class DecoratedTurnPointConditionBuilder implements ConvertibleBuilder<DecoratedTurnPointCondition> {
    private CompareOperator compareOperator;
    private BigDecimal breakPrice;
    private BinaryFactorType binaryFactorType = BinaryFactorType.PERCENT;
    private BigDecimal turnBackPercent;
    private BigDecimal turnBackIncrement;
    private Boolean useGuaranteedPrice = false;
    private Boolean broken = false;
    private BigDecimal extremePrice;

    private BigDecimal baselinePrice;

    private DelayConfirmBuilder delayConfirm = new DelayConfirmBuilder();
    private DeviationCtrlBuilder deviationCtrl = new DeviationCtrlBuilder();

    private int turnPointDelayConfirmedCount;
    private int crossDelayConfirmedCount;

    public void setCompareOperator(CompareOperator compareOperator) {
        this.compareOperator = compareOperator;
    }

    public void setBreakPrice(BigDecimal breakPrice) {
        this.breakPrice = breakPrice;
    }

    public void setBinaryFactorType(BinaryFactorType binaryFactorType) {
        this.binaryFactorType = binaryFactorType;
    }

    public void setTurnBackPercent(BigDecimal turnBackPercent) {
        this.turnBackPercent = turnBackPercent;
    }

    public void setTurnBackIncrement(BigDecimal turnBackIncrement) {
        this.turnBackIncrement = turnBackIncrement;
    }

    public void setUseGuaranteedPrice(Boolean useGuaranteedPrice) {
        this.useGuaranteedPrice = useGuaranteedPrice;
    }

    public void setBroken(Boolean broken) {
        this.broken = broken;
    }

    public void setExtremePrice(BigDecimal extremePrice) {
        this.extremePrice = extremePrice;
    }

    public void setBaselinePrice(BigDecimal baselinePrice) {
        this.baselinePrice = baselinePrice;
    }

    public DelayConfirmBuilder getDelayConfirm() {
        return delayConfirm;
    }

    public DeviationCtrlBuilder getDeviationCtrl() {
        return deviationCtrl;
    }

    public void setTurnPointDelayConfirmedCount(int turnPointDelayConfirmedCount) {
        this.turnPointDelayConfirmedCount = turnPointDelayConfirmedCount;
    }

    public void setCrossDelayConfirmedCount(int crossDelayConfirmedCount) {
        this.crossDelayConfirmedCount = crossDelayConfirmedCount;
    }

    @Override
    public DecoratedTurnPointCondition build() {
        if (broken) {
            Preconditions.checkNotNull(extremePrice, "extremePrice cannot be null when broken");
        }
        return new DecoratedTurnPointCondition(new TurnPointCondition(compareOperator,
                                                                      breakPrice,
                                                                      binaryFactorType,
                                                                      turnBackPercent,
                                                                      turnBackIncrement,
                                                                      useGuaranteedPrice),
                                               baselinePrice,
                                               delayConfirm.build(),
                                               deviationCtrl.build(),
                                               turnPointDelayConfirmedCount,
                                               crossDelayConfirmedCount);
    }
}
