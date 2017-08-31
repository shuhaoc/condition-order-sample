package me.caosh.condition.domain.model.order.plan;

/**
 * Created by caosh on 2017/8/23.
 */
public interface TradePlanVisitor {
    void visitSingleDirectionTradePlan(SingleDirectionTradePlan singleDirectionTradePlan);

    void visitDoubleDirectionTradePlan(DoubleDirectionTradePlan doubleDirectionTradePlan);

    void visitAutoPurchaseTradePlan(AutoPurchaseTradePlan autoPurchaseTradePlan);
}
