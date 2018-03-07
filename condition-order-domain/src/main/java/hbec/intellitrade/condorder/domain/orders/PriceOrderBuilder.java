package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanBuilder;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class PriceOrderBuilder implements ConvertibleBuilder<PriceOrder> {
    private Long orderId;
    private TradeCustomerInfo tradeCustomerInfo;
    private SecurityInfoBuilder securityInfo = new SecurityInfoBuilder();
    private PriceCondition priceCondition;
    private LocalDateTime expireTime;
    private TradePlanBuilder tradePlan = new TradePlanBuilder();
    private OrderState orderState;

    public PriceOrderBuilder setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public TradeCustomerInfo getTradeCustomerInfo() {
        return tradeCustomerInfo;
    }

    public PriceOrderBuilder setTradeCustomerInfo(TradeCustomerInfo tradeCustomerInfo) {
        this.tradeCustomerInfo = tradeCustomerInfo;
        return this;
    }

    public SecurityInfoBuilder getSecurityInfo() {
        return securityInfo;
    }

    public PriceOrderBuilder setSecurityInfo(SecurityInfoBuilder securityInfo) {
        this.securityInfo = securityInfo;
        return this;
    }

    public PriceCondition getPriceCondition() {
        return priceCondition;
    }

    public void setPriceCondition(PriceCondition priceCondition) {
        this.priceCondition = priceCondition;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public TradePlanBuilder getTradePlan() {
        return tradePlan;
    }

    public PriceOrderBuilder setTradePlan(TradePlanBuilder tradePlan) {
        this.tradePlan = tradePlan;
        return this;
    }

    public PriceOrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    @Override
    public PriceOrder build() {
        return new PriceOrder(
                orderId,
                tradeCustomerInfo,
                securityInfo.build(),
                priceCondition,
                expireTime,
                (BasicTradePlan) tradePlan.build(),
                orderState);
    }
}