package me.caosh.condition.domain.model.order.newstock;

import com.google.common.collect.Lists;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityInfoConstants;
import me.caosh.condition.domain.model.newstock.NewStock;
import me.caosh.condition.domain.model.order.AbstractConditionOrder;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.EntrustWithoutMarket;
import me.caosh.condition.domain.model.order.TimeDriven;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.TriggerContext;
import me.caosh.condition.domain.model.order.TriggerPhaseListener;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.AutoPurchaseTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.EntrustResultAware;
import me.caosh.condition.domain.model.trade.NewStockPurchaseOnTrigger;
import me.caosh.condition.domain.model.trade.OrderType;

import java.util.List;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class NewStockOrder extends AbstractConditionOrder implements TimeDriven, NewStockPurchaseOnTrigger,
        EntrustWithoutMarket, EntrustResultAware, TriggerPhaseListener {
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
    public List<EntrustCommand> createEntrustCommand(List<NewStock> currentPurchasable) {
        return Lists.transform(currentPurchasable, this::createPurchaseCommand);
    }

    private EntrustCommand createPurchaseCommand(NewStock newStock) {
        SecurityInfo securityInfo = new SecurityInfo(SecurityType.STOCK, newStock.getPurchaseCode(),
                newStock.getSecurityExchange(), newStock.getPurchaseName());
        return new EntrustCommand(getCustomerIdentity(), securityInfo, ExchangeType.QUOTA_PURCHASE,
                newStock.getIssuePrice(), newStock.getPurchaseUpperLimit(), OrderType.LIMITED);
    }

    @Override
    public void afterEntrustReturned(TriggerContext triggerContext, EntrustResult entrustResult) {
        newStockPurchaseCondition.increasePurchasedCount();
    }

    @Override
    public void afterEntrustCommandExecuted(TriggerContext triggerContext) {
        newStockPurchaseCondition.setTriggeredToday();
    }

    @Override
    public TradePlan getTradePlan() {
        return autoPurchaseTradePlan;
    }
}
