package hbec.intellitrade.condorder.domain.orders.price;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.delayconfirm.DelayConfirmCounterExtractor;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.trackindex.NoneTrackedIndex;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndex;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.MarketClosedEventListener;
import hbec.intellitrade.strategy.domain.MutableStrategy;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmConditionFactory;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCounter;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlConditionFactory;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlParam;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrlParam;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;
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
     * 原始的价格条件
     */
    private final PriceCondition priceCondition;
    /**
     * 组合条件，组合了延迟确认、偏差控制和价格条件等条件
     */
    private final MarketCondition compositeCondition;
    /**
     * 延迟确认计数器包装器，用于为实现{@link MutableStrategy}和{@link MarketClosedEventListener}提供便利
     */
    private final DelayConfirmCounterExtractor delayConfirmCounterExtractor;

    /**
     * 构造价格条件单（基本参数）
     *
     * @param orderId           条件单ID
     * @param tradeCustomerInfo 客户标识信息
     * @param orderState        条件单状态
     * @param securityInfo      交易证券信息
     * @param priceCondition    价格条件
     * @param expireTime        过期时间，可为空
     * @param tradePlan         交易计划
     */
    public PriceOrder(Long orderId,
                      TradeCustomerInfo tradeCustomerInfo,
                      OrderState orderState,
                      SecurityInfo securityInfo,
                      PriceCondition priceCondition,
                      LocalDateTime expireTime,
                      BasicTradePlan tradePlan) {
        this(orderId,
             tradeCustomerInfo,
             orderState,
             securityInfo,
             priceCondition,
             expireTime,
             tradePlan,
             NoneTrackedIndex.NONE,
             NoneMonitorTimeRange.NONE,
             DisabledDelayConfirm.DISABLED,
             null,
             DisabledDeviationCtrlParam.DISABLED);
    }

    /**
     * 构造价格条件单（全参数）
     *
     * @param orderId                 条件单ID
     * @param tradeCustomerInfo       客户标识信息
     * @param orderState              条件单状态
     * @param securityInfo            交易证券信息
     * @param priceCondition          价格条件
     * @param expireTime              过期时间，可为空
     * @param tradePlan               交易计划
     * @param trackedIndexInfo        跟踪指数信息
     * @param monitorTimeRange        监控时段
     * @param delayConfirm       延迟确认参数
     * @param singleDelayConfirmCount 当前延迟确认次数，可为空
     * @param deviationCtrlParam      偏差控制参数
     */
    public PriceOrder(Long orderId,
                      TradeCustomerInfo tradeCustomerInfo,
                      OrderState orderState,
                      SecurityInfo securityInfo,
                      PriceCondition priceCondition,
                      LocalDateTime expireTime,
                      BasicTradePlan tradePlan,
                      TrackedIndex trackedIndexInfo,
                      MonitorTimeRange monitorTimeRange,
                      DelayConfirm delayConfirm,
                      SingleDelayConfirmCount singleDelayConfirmCount,
                      DeviationCtrlParam deviationCtrlParam) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              trackedIndexInfo,
              expireTime,
              monitorTimeRange,
              delayConfirm,
              deviationCtrlParam,
              tradePlan
        );
        this.priceCondition = priceCondition;

        PredictableMarketCondition deviationCtrlWrappedCondition = DeviationCtrlConditionFactory.INSTANCE.wrap(
                priceCondition,
                deviationCtrlParam);

        int confirmedCount = singleDelayConfirmCount != null ? singleDelayConfirmCount.getConfirmedCount() : 0;
        this.compositeCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                deviationCtrlWrappedCondition,
                delayConfirm,
                confirmedCount);
        this.delayConfirmCounterExtractor = new DelayConfirmCounterExtractor(compositeCondition);
    }

    @Override
    protected MarketCondition getCondition() {
        return compositeCondition;
    }

    @Override
    public Condition getRawCondition() {
        return priceCondition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.PRICE;
    }

    /**
     * 获取当前延迟确认次数，未开启延迟确认时返回{@link Optional#absent()}
     * <p>
     * 构建DTO时调用
     *
     * @return 当前延迟确认次数
     */
    public Optional<SingleDelayConfirmCount> getDelayConfirmCount() {
        return delayConfirmCounterExtractor
                .getCounter()
                .transform(new Function<DelayConfirmCounter, SingleDelayConfirmCount>() {
                    @Override
                    public SingleDelayConfirmCount apply(DelayConfirmCounter delayConfirmCounter) {
                        return new SingleDelayConfirmCount(delayConfirmCounter.getConfirmedCount());
                    }
                });
    }

    @Override
    public boolean isDirty() {
        return delayConfirmCounterExtractor.isDirty();
    }

    @Override
    public void clearDirty() {
        delayConfirmCounterExtractor.clearDirtyIfNeed();
    }

    @Override
    public boolean isPersistentPropertyDirty() {
        // 延迟确认次数不需要持久化
        return false;
    }

    @Override
    public void onMarketClosed(LocalDateTime localDateTime) {
        // 盘后清除延迟确认次数
        delayConfirmCounterExtractor.resetCounterIfNeed();
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

        return compositeCondition.equals(that.compositeCondition);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + compositeCondition.hashCode();
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
