package hbec.intellitrade.condorder.domain;

import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.tradeplan.SingleEntrustTradePlan;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ExplicitTradingSecurityOrder}骨架类
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public abstract class AbstractExplicitTradingSecurityOrder extends AbstractConditionOrder
        implements ExplicitTradingSecurityOrder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExplicitTradingSecurityOrder.class);

    private final SecurityInfo securityInfo;

    public AbstractExplicitTradingSecurityOrder(Long orderId,
                                                TradeCustomerInfo tradeCustomerInfo,
                                                OrderState orderState,
                                                SecurityInfo securityInfo,
                                                LocalDateTime expireTime) {
        super(orderId, tradeCustomerInfo, orderState, expireTime);
        this.securityInfo = securityInfo;
    }

    @Override
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    @Override
    public void onTradeSignal(TriggerTradingContext triggerTradingContext) {
        Preconditions.checkArgument(getOrderState() == OrderState.ACTIVE, "Order state should be ACTIVE");

        EntrustCommand entrustCommand = createEntrustCommand((TradeSignal) triggerTradingContext.getSignal(),
                                                             triggerTradingContext);
        executeEntrustCommand(triggerTradingContext, entrustCommand);
        afterEntrustCommandsExecuted(triggerTradingContext);
    }

    /**
     * 交易信号处理行为
     *
     * @param tradeSignal           交易信号
     * @param tradingMarketSupplier 交易标的实时行情supplier
     * @return 交易指令
     */
    protected EntrustCommand createEntrustCommand(TradeSignal tradeSignal,
                                                  TradingMarketSupplier tradingMarketSupplier) {
        SingleEntrustTradePlan singleEntrustTradePlan = (SingleEntrustTradePlan) getTradePlan();
        return singleEntrustTradePlan.createEntrustCommand(tradeSignal, getSecurityInfo(), tradingMarketSupplier);
    }

    /**
     * 所有委托完成事件
     *
     * @param triggerTradingContext 触发交易上下文
     */
    protected void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        setOrderState(OrderState.TERMINATED);
    }
}
