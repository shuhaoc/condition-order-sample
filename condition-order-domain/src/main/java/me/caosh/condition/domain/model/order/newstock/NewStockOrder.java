package me.caosh.condition.domain.model.order.newstock;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfoConstants;
import me.caosh.condition.domain.model.order.AbstractConditionOrder;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.TimeDriven;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.AutoPurchaseTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.MultipleEntrustOnTrigger;

import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class NewStockOrder extends AbstractConditionOrder implements TimeDriven, MultipleEntrustOnTrigger {
    private final AutoPurchaseTradePlan autoPurchaseTradePlan = new AutoPurchaseTradePlan();
    private final NewStockPurchaseCondition newStockPurchaseCondition;

    public NewStockOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted,
                         NewStockPurchaseCondition newStockPurchaseCondition, OrderState orderState) {
        super(orderId, customerIdentity, deleted, SecurityInfoConstants.NEW_STOCK_PURCHASE, NativeStrategyInfo.NEW_STOCK, orderState);
        this.newStockPurchaseCondition = newStockPurchaseCondition;
    }

    public NewStockPurchaseCondition getNewStockPurchaseCondition() {
        return newStockPurchaseCondition;
    }

    @Override
    public Condition getCondition() {
        return getNewStockPurchaseCondition();
    }

    @Override
    public TradeSignal onSecondTick() {
        if (newStockPurchaseCondition.isSatisfiedNow()) {
            return SignalFactory.getInstance().general();
        }
        return SignalFactory.getInstance().none();
    }

    @Override
    public List<EntrustCommand> createEntrustCommands(TradeSignal signal, RealTimeMarket realTimeMarket) {
        return Collections.emptyList();
    }

    @Override
    public TradePlan getTradePlan() {
        return autoPurchaseTradePlan;
    }
}
