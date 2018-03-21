package hbec.intellitrade.condorder.domain.orders.price;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.trackindex.NoneTrackedIndex;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndex;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.MarketClosedEventListener;
import hbec.intellitrade.strategy.domain.MutableStrategy;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import org.joda.time.LocalDateTime;

/**
 * 价格条件单
 * <p>
 * 一次性条件单，触发条件为到价触发，使用单向交易计划
 * <p>
 * 支持的特性：
 * <ol>
 * <li>跟踪指数</li>
 * <li>监控时段</li>
 * <li>延迟确认</li>
 * <li>偏差控制</li>
 * </ol>
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/1
 */
public class PriceOrder extends AbstractSimpleMarketConditionOrder implements MutableStrategy, MarketClosedEventListener {
    /**
     * 组合条件，组合了延迟确认、偏差控制和价格条件等条件
     */
    private final PriceConditionFacade condition;

    /**
     * 构造价格条件单（最少参数）
     *
     * @param orderId           条件单ID
     * @param tradeCustomerInfo 客户标识信息
     * @param orderState        条件单状态
     * @param securityInfo      交易证券信息
     * @param priceCondition    价格条件
     * @param tradePlan         交易计划
     */
    public PriceOrder(Long orderId,
                      TradeCustomerInfo tradeCustomerInfo,
                      OrderState orderState,
                      SecurityInfo securityInfo,
                      PriceCondition priceCondition,
                      BasicTradePlan tradePlan) {
        this(orderId,
             tradeCustomerInfo,
             orderState,
             securityInfo,
             new PriceConditionFacade(priceCondition, DisabledDelayConfirm.DISABLED, DisabledDeviationCtrl.DISABLED, 0),
             null,
             tradePlan,
             NoneTrackedIndex.NONE,
             NoneMonitorTimeRange.NONE
        );
    }


    /**
     * 构造价格条件单（全参数）
     *
     * @param orderId           条件单ID
     * @param tradeCustomerInfo 客户标识信息
     * @param orderState        条件单状态
     * @param securityInfo      交易证券信息
     * @param condition         组合行情条件，包含价格条件、延迟确认、偏差控制等因子
     * @param expireTime        过期时间，可为空
     * @param tradePlan         交易计划
     * @param trackedIndexInfo  跟踪指数信息
     * @param monitorTimeRange  监控时段
     */
    public PriceOrder(Long orderId,
                      TradeCustomerInfo tradeCustomerInfo,
                      OrderState orderState,
                      SecurityInfo securityInfo,
                      PriceConditionFacade condition,
                      LocalDateTime expireTime,
                      BasicTradePlan tradePlan,
                      TrackedIndex trackedIndexInfo,
                      MonitorTimeRange monitorTimeRange) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              trackedIndexInfo,
              expireTime,
              monitorTimeRange,
              null,
              null,
              tradePlan);
        this.condition = condition;
    }

    @Override
    public MarketCondition getCondition() {
        return condition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.PRICE;
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
        // 延迟确认次数不需要持久化
        return false;
    }

    @Override
    public void onMarketClosed(LocalDateTime localDateTime) {
        // 盘后清除延迟确认次数
        condition.resetCounter();
    }

    @Override
    public boolean equals(Object o) {
        // 仅比较组合条件即可，组合条件组合了所有条件
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        PriceOrder that = (PriceOrder) o;

        return condition.equals(that.condition);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + condition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceOrder.class).omitNullValues()
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
