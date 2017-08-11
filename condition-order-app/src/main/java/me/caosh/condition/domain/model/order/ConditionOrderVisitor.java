package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.SecurityInfo;

/**
 * Created by caosh on 2017/8/2.
 */
public interface ConditionOrderVisitor {
    void visitSecurityInfo(SecurityInfo securityInfo);

    void visitEntrustParameter(TradePlan entrustParameter);

    void visitSimplePriceCondition(SimplePriceCondition simplePriceCondition);
}
