package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrlParam;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import org.joda.time.LocalDateTime;

/**
 * 拐点条件单（拐点买入、回落卖出）
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/19
 */
public class TurnPointOrder extends AbstractSimpleMarketConditionOrder {
    private final TurnPointCondition turnPointCondition;

    public TurnPointOrder(Long orderId,
                          TradeCustomerInfo tradeCustomerInfo,
                          OrderState orderState,
                          SecurityInfo securityInfo,
                          TurnPointCondition turnPointCondition,
                          LocalDateTime expireTime,
                          BasicTradePlan tradePlan) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              null,
              expireTime,
              NoneMonitorTimeRange.NONE,
              DisabledDelayConfirmParam.DISABLED,
              DisabledDeviationCtrlParam.DISABLED,
              tradePlan);
        this.turnPointCondition = turnPointCondition;
    }

    public TurnPointCondition getTurnPointCondition() {
        return turnPointCondition;
    }

    @Override
    public MarketCondition getCondition() {
        return turnPointCondition;
    }

    @Override
    public Condition getRawCondition() {
        return turnPointCondition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.TURN_POINT;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .addValue(super.toString())
                          .add("turnPointCondition", turnPointCondition)
                          .toString();
    }
}
