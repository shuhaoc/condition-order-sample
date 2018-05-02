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
    private BigDecimal breakPercent;
    private BigDecimal turnBackPercent;
    private BigDecimal breakIncrement;
    private BigDecimal turnBackIncrement;

    public void setBreakPercent(BigDecimal breakPercent) {
        this.breakPercent = breakPercent;
    }

    public void setTurnBackPercent(BigDecimal turnBackPercent) {
        this.turnBackPercent = turnBackPercent;
    }

    public void setBreakIncrement(BigDecimal breakIncrement) {
        this.breakIncrement = breakIncrement;
    }

    public void setTurnBackIncrement(BigDecimal turnBackIncrement) {
        this.turnBackIncrement = turnBackIncrement;
    }

    public GridSubCondition build(BigDecimal basePrice,
            CompareOperator compareOperator,
            BinaryFactorType binaryFactorType,
            boolean useGuaranteedPrice) {
        BinaryTargetPriceFactor mainFactor = BinaryTargetPriceFactorFactory.INSTANCE.create(compareOperator,
                binaryFactorType,
                breakPercent,
                breakIncrement);
        if (!BigDecimals.nullOrZero(turnBackPercent) || !BigDecimals.nullOrZero(turnBackIncrement)) {
            BinaryTargetPriceFactor turnBackFactor = BinaryTargetPriceFactorFactory.INSTANCE.create(compareOperator.reverse(),
                    binaryFactorType,
                    turnBackPercent,
                    turnBackIncrement);
            return new InflexionSubCondition(mainFactor, turnBackFactor, basePrice, useGuaranteedPrice);
        }
        return new SimpleSubCondition(mainFactor, basePrice);
    }
}
