package hbec.intellitrade.strategy.domain.condition.deviation;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;
import hbec.intellitrade.strategy.domain.shared.BigDecimalRanges;
import hbec.intellitrade.strategy.domain.shared.Range;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

import java.math.BigDecimal;

/**
 * 启用状态下的偏差控制条件包装类，包装可预测的行情条件和偏差控制参数，实现偏差控制条件接口
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/28
 */
public class EnabledDeviationCtrlCondition implements DeviationCtrlCondition, PredictableMarketCondition {
    private final PredictableMarketCondition predictableMarketCondition;
    private final BigDecimal limitPercent;

    public EnabledDeviationCtrlCondition(PredictableMarketCondition predictableMarketCondition,
                                         BigDecimal limitPercent) {
        this.predictableMarketCondition = predictableMarketCondition;
        this.limitPercent = limitPercent;
    }

    public BigDecimal getLimitPercent() {
        return limitPercent;
    }

    @Override
    public TargetPriceFactor getTargetPriceFactor() {
        return predictableMarketCondition.getTargetPriceFactor();
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        TradeSignal tradeSignal = predictableMarketCondition.onMarketTick(realTimeMarket);
        if (!tradeSignal.isValid()) {
            return tradeSignal;
        }

        boolean deviationLimitExceeded = isDeviationLimitExceeded(realTimeMarket);
        if (!deviationLimitExceeded) {
            return tradeSignal;
        }

        return tradeSignal.withDeviationExceeded();
    }

    /**
     * 在行情条件达成，且开启偏差控制情况下，判断是否超出偏差限制
     *
     * @param realTimeMarket 实时行情
     * @return 超出偏差控制
     */
    private boolean isDeviationLimitExceeded(RealTimeMarket realTimeMarket) {
        TargetPriceFactor targetPriceFactor = predictableMarketCondition.getTargetPriceFactor();
        BigDecimal targetPrice = targetPriceFactor.getTargetPrice();

        // 偏差控制允许的区间
        // TODO: 是否可以不使用区间，直接使用>=或<=
        Range<BigDecimal> deviationLimitedRange = BigDecimalRanges.openCenterWithPercent(
                targetPrice, limitPercent);

        boolean inRange = deviationLimitedRange.isInRange(realTimeMarket.getCurrentPrice());
        return !inRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnabledDeviationCtrlCondition that = (EnabledDeviationCtrlCondition) o;

        if (!predictableMarketCondition.equals(that.predictableMarketCondition)) {
            return false;
        }
        return limitPercent.equals(that.limitPercent);
    }

    @Override
    public int hashCode() {
        int result = predictableMarketCondition.hashCode();
        result = 31 * result + limitPercent.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EnabledDeviationCtrlCondition.class).omitNullValues()
                          .add("limitPercent", limitPercent)
                          .add("predictableMarketCondition", predictableMarketCondition)
                          .toString();
    }
}
