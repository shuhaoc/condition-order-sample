package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactor;

import java.math.BigDecimal;

/**
 * 网格交易单个方向上的行情条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
public interface GridSubCondition extends PredictableMarketCondition {
    /**
     * 变更基准价（prototype模式）
     *
     * @param basePrice 基准价
     * @return 变更基准价后的条件对象
     */
    GridSubCondition changeBasePrice(BigDecimal basePrice);

    /**
     * 获取主因子
     *
     * @return 主因子
     */
    BinaryTargetPriceFactor getMainFactor();

    /**
     * 获取折返因子
     *
     * @return 折返因子，可能为空
     */
    BinaryTargetPriceFactor getTurnBackFactor();
}
