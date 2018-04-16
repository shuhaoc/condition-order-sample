package hbec.intellitrade.conditionorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.conditionorder.domain.trackindex.TrackedIndex;
import hbec.intellitrade.conditionorder.domain.tradeplan.BaseTradePlan;
import hbec.intellitrade.strategy.domain.MarketClosedEventListener;
import hbec.intellitrade.strategy.domain.MutableStrategy;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import org.joda.time.LocalDateTime;

import java.util.Objects;

/**
 * 拐点条件单（拐点买入、回落卖出）
 * <p>
 * 触发条件主要为拐点因子，交易计划为基本的单次委托，触发一次后立即正常终止
 * <p>
 * 踩线交易：价格越过底线价直接正常终止，不执行委托，下跌拐点结构中，底线价低于突破价，上涨回落结构中高于
 * <p>
 * 支持的条件特性：
 * <ol>
 * <li>跟踪指数</li>
 * <li>监控时段</li>
 * <li>延迟确认</li>
 * <li>偏差控制</li>
 * <li>保底价触发</li>
 * <li>踩线交易</li>
 * </ol>
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/19
 */
public class TurnPointOrder extends AbstractSimpleMarketConditionOrder implements MutableStrategy, MarketClosedEventListener {
    private final DecoratedTurnPointCondition condition;

    /**
     * 构造拐点条件单（最少参数）
     *
     * @param orderId            条件单ID
     * @param tradeCustomerInfo  客户标识信息
     * @param orderState         条件单状态
     * @param securityInfo       交易证券信息
     * @param turnPointCondition 拐点条件
     * @param tradePlan          交易计划
     */
    public TurnPointOrder(Long orderId,
                          TradeCustomerInfo tradeCustomerInfo,
                          OrderState orderState,
                          SecurityInfo securityInfo,
                          TurnPointCondition turnPointCondition,
                          BaseTradePlan tradePlan) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              null,
              null,
              NoneMonitorTimeRange.NONE,
              tradePlan);
        this.condition = new DecoratedTurnPointCondition(turnPointCondition,
                                                         null,
                                                         DisabledDelayConfirm.DISABLED,
                                                         DisabledDeviationCtrl.DISABLED,
                                                         0,
                                                         0);
    }

    /**
     * 构造拐点条件单（全参数）
     *
     * @param orderId           条件单ID
     * @param tradeCustomerInfo 客户标识信息
     * @param orderState        条件单状态
     * @param securityInfo      交易证券信息
     * @param condition         组合行情条件，包含拐点、踩线、延迟确认、偏差控制等因子
     * @param expireTime        过期时间，空视为永久有效
     * @param tradePlan         交易计划
     * @param trackedIndexInfo  跟踪指数信息
     * @param monitorTimeRange  监控时段
     */
    public TurnPointOrder(Long orderId,
                          TradeCustomerInfo tradeCustomerInfo,
                          OrderState orderState,
                          SecurityInfo securityInfo,
                          DecoratedTurnPointCondition condition,
                          LocalDateTime expireTime,
                          BaseTradePlan tradePlan,
                          TrackedIndex trackedIndexInfo,
                          MonitorTimeRange monitorTimeRange) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              trackedIndexInfo,
              expireTime,
              monitorTimeRange,
              tradePlan);
        Preconditions.checkNotNull(condition, "condition cannot be null");
        this.condition = condition;
    }

    @Override
    public MarketCondition getCondition() {
        return condition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.TURN_POINT;
    }

    @Override
    public boolean isDirty() {
        return condition.isDirty();
    }

    @Override
    public void clearDirty() {
        condition.clearDirty();
    }

    @Override
    public boolean isPersistentPropertyDirty() {
        // 延迟确认次数不需要持久化，仅拐点状态需要
        return condition.getRawTurnPointCondition().isDirty();
    }

    @Override
    public void onMarketClosed(LocalDateTime localDateTime) {
        condition.resetCounter();
    }

    /**
     * 响应穿越底线价信号
     */
    public void onCrossBaseline() {
        setOrderState(OrderState.TERMINATED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TurnPointOrder that = (TurnPointOrder) o;
        return Objects.equals(condition, that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), condition);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TurnPointOrder.class).omitNullValues()
                          .add("orderId", getOrderId())
                          .add("customer", getCustomer())
                          .add("orderState", getOrderState())
                          .add("securityInfo", getSecurityInfo())
                          .add("trackedIndexInfo", getTrackedIndex())
                          .add("condition", getCondition())
                          .add("expireTime", getExpireTime())
                          .add("tradePlan", getTradePlan())
                          .add("monitorTimeRange", getMonitorTimeRange())
                          .toString();
    }
}
