package hbec.intellitrade.condorder.domain.tradeplan;

/**
 * Created by caosh on 2017/8/23.
 */
public interface TradePlanVisitor {
    void visitSingleDirectionTradePlan(BasicTradePlan basicTradePlan);

    void visitDoubleDirectionTradePlan(DoubleDirectionTradePlan doubleDirectionTradePlan);

    void visitAutoPurchaseTradePlan(AutoPurchaseTradePlan autoPurchaseTradePlan);
}
