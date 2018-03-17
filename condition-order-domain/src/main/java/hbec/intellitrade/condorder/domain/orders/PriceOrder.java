package hbec.intellitrade.condorder.domain.orders;

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
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmConditionFactory;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCounter;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlConditionFactory;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlParam;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrlParam;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import org.joda.time.LocalDateTime;

/**
 * 价格条件单
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/1
 */
public class PriceOrder extends AbstractSimpleMarketConditionOrder implements MutableStrategy, MarketClosedEventListener {

    private final PriceCondition priceCondition;
    private final MarketCondition compositeCondition;
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
             DisabledDelayConfirmParam.DISABLED,
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
     * @param delayConfirmParam       延迟确认参数
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
                      DelayConfirmParam delayConfirmParam,
                      SingleDelayConfirmCount singleDelayConfirmCount,
                      DeviationCtrlParam deviationCtrlParam) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              trackedIndexInfo,
              expireTime,
              monitorTimeRange,
              delayConfirmParam,
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
                delayConfirmParam,
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
        delayConfirmCounterExtractor.resetCounterIfNeed();
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
