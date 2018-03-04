package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.SingleEntrustTradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 普通条件单，一次仅触发一笔委托
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/1
 */
public abstract class AbstractGeneralConditionOrder extends AbstractConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractGeneralConditionOrder.class);

    public AbstractGeneralConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                         StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
    }

    @Override
    public void onTradeSignal(TradeSignal tradeSignal, TradeCustomer tradeCustomer, TradingMarketSupplier tradingMarketSupplier) {
        SingleEntrustTradePlan singleEntrustTradePlan = (SingleEntrustTradePlan) getTradePlan();
        EntrustCommand entrustCommand = singleEntrustTradePlan.createEntrustCommand(tradeSignal, getSecurityInfo(),
                tradingMarketSupplier);
        EntrustResult entrustResult = tradeCustomer.entrust(entrustCommand);
        logger.info("Entrust result <== {}", entrustResult);
    }
}
