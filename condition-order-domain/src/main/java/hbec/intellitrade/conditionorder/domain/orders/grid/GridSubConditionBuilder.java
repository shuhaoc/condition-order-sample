package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactorFactory;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.shared.BigDecimals;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
public class GridSubConditionBuilder {
    private BinaryPriceFactorBuilder mainFactor = new BinaryPriceFactorBuilder();
    private BinaryPriceFactorBuilder turnBackFactor = new BinaryPriceFactorBuilder();

    public BinaryPriceFactorBuilder getMainFactor() {
        return mainFactor;
    }

    public BinaryPriceFactorBuilder getTurnBackFactor() {
        return turnBackFactor;
    }

    public static class BinaryPriceFactorBuilder {
        private BigDecimal percent;
        private BigDecimal increment;

        public boolean isValid() {
            return !BigDecimals.nullOrZero(percent) || !BigDecimals.nullOrZero(increment);
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

    public GridSubCondition build(BigDecimal basePrice,
            CompareOperator compareOperator,
            BinaryFactorType binaryFactorType,
            boolean useGuaranteedPrice) {
        BinaryTargetPriceFactor theMainFactor = BinaryTargetPriceFactorFactory.INSTANCE.create(compareOperator,
                binaryFactorType,
                mainFactor.getPercent(),
                mainFactor.getIncrement());
        if (turnBackFactor.isValid()) {
            BinaryTargetPriceFactor theTurnBackFactor = BinaryTargetPriceFactorFactory.INSTANCE.create(compareOperator.reverse(),
                    binaryFactorType,
                    turnBackFactor.getPercent(),
                    turnBackFactor.getIncrement());
            return new InflexionSubCondition(theMainFactor, theTurnBackFactor, basePrice, useGuaranteedPrice);
        }
        return new SimpleSubCondition(theMainFactor, basePrice);
    }
}
