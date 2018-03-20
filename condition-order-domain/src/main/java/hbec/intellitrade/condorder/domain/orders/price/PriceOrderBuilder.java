package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.condorder.domain.delayconfirm.count.DelayConfirmCount;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndexInfoBuilder;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanBuilder;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmBuilder;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlBuilder;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRangeBuilder;
import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class PriceOrderBuilder implements ConvertibleBuilder<PriceOrder> {
    private Long orderId;
    private TradeCustomerInfoBuilder customer = new TradeCustomerInfoBuilder();
    private OrderState orderState;
    private SecurityInfoBuilder securityInfo = new SecurityInfoBuilder();
    private TrackedIndexInfoBuilder trackedIndex = new TrackedIndexInfoBuilder();
    private DecoratedPriceConditionBuilder condition = new DecoratedPriceConditionBuilder();
    private LocalDateTime expireTime;
    private TradePlanBuilder tradePlan = new TradePlanBuilder();
    private MonitorTimeRangeBuilder monitorTimeRange = new MonitorTimeRangeBuilder();

    public PriceOrderBuilder setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public TradeCustomerInfoBuilder getCustomer() {
        return customer;
    }

    public PriceOrderBuilder setCustomer(TradeCustomerInfoBuilder customer) {
        this.customer = customer;
        return this;
    }

    public PriceOrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public SecurityInfoBuilder getSecurityInfo() {
        return securityInfo;
    }

    public PriceOrderBuilder setSecurityInfo(SecurityInfoBuilder securityInfo) {
        this.securityInfo = securityInfo;
        return this;
    }

    public TrackedIndexInfoBuilder getTrackedIndex() {
        return trackedIndex;
    }

    public void setTrackedIndex(TrackedIndexInfoBuilder trackedIndex) {
        this.trackedIndex = trackedIndex;
    }

    public DecoratedPriceConditionBuilder getCondition() {
        return condition;
    }

    public void setCondition(DecoratedPriceConditionBuilder condition) {
        this.condition = condition;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public TradePlanBuilder getTradePlan() {
        return tradePlan;
    }

    public void setTradePlan(TradePlanBuilder tradePlan) {
        this.tradePlan = tradePlan;
    }

    public DelayConfirmBuilder getDelayConfirm() {
        return delayConfirm;
    }

    public void setDelayConfirm(DelayConfirmBuilder delayConfirm) {
        this.delayConfirm = delayConfirm;
    }

    public void setDelayConfirmCount(DelayConfirmCount delayConfirmCount) {
        this.delayConfirmCount = delayConfirmCount;
    }

    public MonitorTimeRangeBuilder getMonitorTimeRange() {
        return monitorTimeRange;
    }

    public void setMonitorTimeRange(MonitorTimeRangeBuilder monitorTimeRange) {
        this.monitorTimeRange = monitorTimeRange;
    }

    public DeviationCtrlBuilder getDeviationCtrl() {
        return deviationCtrl;
    }

    public void setDeviationCtrl(DeviationCtrlBuilder deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
    }

    @Override
    public PriceOrder build() {
        DecoratedPriceCondition decoratedPriceCondition = condition
                .setDelayConfirm(delayConfirm)
                .setDelayConfirmCount(delayConfirmCount)
                .setDeviationCtrl(deviationCtrl)
                .build();
        return new PriceOrder(orderId,
                              customer.build(),
                              orderState,
                              securityInfo.build(),
                              condition.build(),
                              expireTime,
                              (BasicTradePlan) tradePlan.build(),
                              trackedIndex.build(),
                              monitorTimeRange.build(),
                              delayConfirm.build(),
                              (SingleDelayConfirmCount) delayConfirmCount,
                              deviationCtrl.build());
    }
}