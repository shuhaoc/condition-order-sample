package hbec.intellitrade.condorder.domain.orders;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import hbec.intellitrade.common.market.index.IndexInfo;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.MarketClosedEventListener;
import hbec.intellitrade.strategy.domain.MutableStrategy;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.*;
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
    private final DelayConfirmParam delayConfirmParam;
    private final MarketCondition compositeCondition;
    private final DeviationCtrlParam deviationCtrlParam;

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
             DisabledDelayConfirmParam.DISABLED,
             null,
             NoneMonitorTimeRange.NONE,
             DisabledDeviationCtrlParam.DISABLED);
    }

    public PriceOrder(Long orderId,
                      TradeCustomerInfo tradeCustomerInfo,
                      OrderState orderState,
                      SecurityInfo securityInfo,
                      PriceCondition priceCondition,
                      LocalDateTime expireTime,
                      BasicTradePlan tradePlan,
                      DelayConfirmParam delayConfirmParam,
                      SingleDelayConfirmCount singleDelayConfirmCount,
                      MonitorTimeRange monitorTimeRange,
                      DeviationCtrlParam deviationCtrlParam) {
        this(orderId,
             tradeCustomerInfo,
             orderState,
             securityInfo,
             null,
             priceCondition,
             expireTime,
             tradePlan,
             delayConfirmParam,
             singleDelayConfirmCount,
             monitorTimeRange,
             deviationCtrlParam);
    }

    public PriceOrder(Long orderId,
                      TradeCustomerInfo tradeCustomerInfo,
                      OrderState orderState,
                      SecurityInfo securityInfo,
                      IndexInfo trackedIndexInfo,
                      PriceCondition priceCondition,
                      LocalDateTime expireTime,
                      BasicTradePlan tradePlan,
                      DelayConfirmParam delayConfirmParam,
                      SingleDelayConfirmCount singleDelayConfirmCount,
                      MonitorTimeRange monitorTimeRange,
                      DeviationCtrlParam deviationCtrlParam) {
        super(orderId,
              tradeCustomerInfo,
              orderState,
              securityInfo,
              trackedIndexInfo,
              expireTime,
              tradePlan,
              monitorTimeRange);
        this.priceCondition = priceCondition;
        this.delayConfirmParam = delayConfirmParam;
        this.deviationCtrlParam = deviationCtrlParam;

        PredictableMarketCondition deviationCtrlWrappedCondition = DeviationCtrlConditionFactory.INSTANCE.wrap(
                priceCondition,
                deviationCtrlParam);

        int confirmedCount = singleDelayConfirmCount != null ? singleDelayConfirmCount.getConfirmedCount() : 0;
        this.compositeCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(deviationCtrlWrappedCondition,
                                                                                 delayConfirmParam,
                                                                                 confirmedCount);
    }

    @Override
    protected MarketCondition getCondition() {
        return compositeCondition;
    }

    public Condition getRawCondition() {
        // TODO: 如何约束不可变性
        return priceCondition;
    }

    public DelayConfirmParam getDelayConfirmParam() {
        return delayConfirmParam;
    }

    public DeviationCtrlParam getDeviationCtrlParam() {
        return deviationCtrlParam;
    }

    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.PRICE;
    }

    public Optional<SingleDelayConfirmCount> getDelayConfirmCount() {
        return getDelayConfirmCounter().transform(new Function<DelayConfirmCounter, SingleDelayConfirmCount>() {
            @Override
            public SingleDelayConfirmCount apply(DelayConfirmCounter delayConfirmCounter) {
                return new SingleDelayConfirmCount(delayConfirmCounter.getConfirmedCount());
            }
        });
    }

    @Override
    public boolean isDirty() {
        Optional<DelayConfirmCounter> delayConfirmCount = getDelayConfirmCounter();
        return delayConfirmCount.isPresent() && delayConfirmCount.get().isDirty();
    }

    @Override
    public void clearDirty() {
        Optional<DelayConfirmCounter> delayConfirmCount = getDelayConfirmCounter();
        if (delayConfirmCount.isPresent()) {
            delayConfirmCount.get().clearDirty();
        }
    }

    private Optional<DelayConfirmCounter> getDelayConfirmCounter() {
        if (compositeCondition instanceof AbstractDelayConfirmCondition) {
            DelayConfirmCounter counter = ((AbstractDelayConfirmCondition) compositeCondition).getCounter();
            return Optional.of(counter);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public boolean isPersistentPropertyDirty() {
        // 延迟确认次数不需要持久化
        return false;
    }

    @Override
    public void onMarketClosed(LocalDateTime localDateTime) {
        Optional<DelayConfirmCounter> delayConfirmCounter = getDelayConfirmCounter();
        if (delayConfirmCounter.isPresent()) {
            delayConfirmCounter.get().reset();
        }
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
                          .add("condition", getCondition())
                          .add("expireTime", getExpireTime())
                          .add("tradePlan", getTradePlan())
                          .add("monitorTimeRange", getMonitorTimeRange())
                          .toString();
    }
}
