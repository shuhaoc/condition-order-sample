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
    private BinaryPriceFactorBuilder turnBackBinaryPriceFactor = new BinaryPriceFactorBuilder();
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

    public BinaryPriceFactorBuilder getTurnBackBinaryPriceFactor() {
        return turnBackBinaryPriceFactor;
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

    public static class BinaryPriceFactorBuilder {
        private BinaryFactorType binaryFactorType = BinaryFactorType.PERCENT;
        private BigDecimal percent;
        private BigDecimal increment;

        public BinaryFactorType getBinaryFactorType() {
            return binaryFactorType;
        }

        public void setBinaryFactorType(BinaryFactorType binaryFactorType) {
            this.binaryFactorType = binaryFactorType;
        }

        public BigDecimal getPercent() {
            return percent;
        }

        public void setPercent(BigDecimal percent) {
            this.percent = percent;
        }

        public BigDecimal getIncrement() {
            return increment;
        }

        public void setIncrement(BigDecimal increment) {
            this.increment = increment;
        }
    }

    @Override
    public DecoratedTurnPointCondition build() {
        if (broken) {
            Preconditions.checkNotNull(extremePrice, "extremePrice cannot be null when broken");
        }
        return new DecoratedTurnPointCondition(new TurnPointCondition(compareOperator,
                                                                      breakPrice,
                                                                      turnBackBinaryPriceFactor.getBinaryFactorType(),
                                                                      turnBackBinaryPriceFactor.getPercent(),
                                                                      turnBackBinaryPriceFactor.getIncrement(),
                                                                      useGuaranteedPrice,
                                                                      broken,
                                                                      extremePrice),
                                               baselinePrice,
                                               delayConfirm.build(),
                                               deviationCtrl.build(),
                                               turnPointDelayConfirmedCount,
                                               crossDelayConfirmedCount);
    }
}
