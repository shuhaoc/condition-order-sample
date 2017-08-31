package me.caosh.condition.domain.model.order.newstock;

import me.caosh.condition.domain.model.order.AbstractConditionOrder;
import me.caosh.condition.domain.model.order.TimeDriven;
import me.caosh.condition.domain.model.order.plan.AutoPurchaseTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.trade.MultipleEntrustOnTrigger;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class NewStockOrder extends AbstractConditionOrder implements TimeDriven, MultipleEntrustOnTrigger {
    private final AutoPurchaseTradePlan autoPurchaseTradePlan = new AutoPurchaseTradePlan();

    @Override
    public TradePlan getTradePlan() {
        return autoPurchaseTradePlan;
    }
}
