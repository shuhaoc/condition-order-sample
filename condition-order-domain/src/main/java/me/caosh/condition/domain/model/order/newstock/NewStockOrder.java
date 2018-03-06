package me.caosh.condition.domain.model.order.newstock;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import me.caosh.condition.domain.model.account.TradeCustomer;
import me.caosh.condition.domain.model.market.SecurityInfoConstants;
import me.caosh.condition.domain.model.newstock.NewStock;
import me.caosh.condition.domain.model.order.AbstractConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.TradingMarketSupplier;
import me.caosh.condition.domain.model.order.TriggerTradingContext;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.AutoPurchaseTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.NewStockPurchaseOnTrigger;
import me.caosh.condition.domain.model.trade.OrderType;

import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class NewStockOrder extends AbstractConditionOrder implements NewStockPurchaseOnTrigger {
    private final AutoPurchaseTradePlan autoPurchaseTradePlan = new AutoPurchaseTradePlan();
    private final NewStockPurchaseCondition newStockPurchaseCondition;

    public NewStockOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo,
                         NewStockPurchaseCondition newStockPurchaseCondition, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, SecurityInfoConstants.NEW_STOCK_PURCHASE, strategyState);
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
    public Condition getRawCondition() {
        return newStockPurchaseCondition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.NEW_STOCK;
    }

    @Override
    public List<EntrustCommand> createEntrustCommand(List<NewStock> currentPurchasable) {
        return Lists.transform(currentPurchasable, new Function<NewStock, EntrustCommand>() {
            @Override
            public EntrustCommand apply(NewStock newStock) {
                return createPurchaseCommand(newStock);
            }
        });
    }

    private EntrustCommand createPurchaseCommand(NewStock newStock) {
        SecurityInfo securityInfo = new SecurityInfo(SecurityType.STOCK, newStock.getPurchaseCode(),
                newStock.getSecurityExchange(), newStock.getPurchaseName());
        return new EntrustCommand(securityInfo, ExchangeType.QUOTA_PURCHASE,
                newStock.getIssuePrice(), newStock.getPurchaseUpperLimit(), OrderType.LIMITED);
    }

    @Override
    public List<EntrustCommand> onTradeSignal(TradeSignal tradeSignal, TradeCustomer tradeCustomer, TradingMarketSupplier tradingMarketSupplier) {
        return Collections.emptyList();
    }

    @Override
    public void afterEntrustSuccess(TriggerTradingContext triggerTradingContext, EntrustResult entrustResult) {
        newStockPurchaseCondition.increasePurchasedCount();
    }

    @Override
    public void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        newStockPurchaseCondition.setTriggeredToday();
    }

    @Override
    public TradePlan getTradePlan() {
        return autoPurchaseTradePlan;
    }
}
