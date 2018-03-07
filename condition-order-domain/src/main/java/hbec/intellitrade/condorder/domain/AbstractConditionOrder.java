package hbec.intellitrade.condorder.domain;

import com.google.common.base.Optional;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.tradeplan.SingleEntrustTradePlan;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustResult;
import me.caosh.condition.domain.model.account.TradeCustomer;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * 条件单骨架，并实现最常见的单委托、单次条件单逻辑
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/2
 */
public abstract class AbstractConditionOrder implements ConditionOrder, AutoTradeAction {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConditionOrder.class);

    private final Long orderId;
    private final TradeCustomerInfo tradeCustomerInfo;
    private StrategyState strategyState;
    private final SecurityInfo securityInfo;
    private final LocalDateTime expireTime;

    public AbstractConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                  StrategyState strategyState) {
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.securityInfo = securityInfo;
        this.expireTime = null;
        this.strategyState = strategyState;
    }

    public AbstractConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                  LocalDateTime expireTime, StrategyState strategyState) {
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.securityInfo = securityInfo;
        this.expireTime = expireTime;
        this.strategyState = strategyState;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public long getStrategyId() {
        return getOrderId().intValue();
    }

    @Override
    public TradeCustomerInfo getCustomer() {
        return tradeCustomerInfo;
    }

    @Override
    public StrategyState getStrategyState() {
        return strategyState;
    }

    protected void setStrategyState(StrategyState strategyState) {
        this.strategyState = strategyState;
    }

    @Override
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    public Optional<LocalDateTime> getExpireTime() {
        return Optional.fromNullable(expireTime);
    }

    protected boolean isMonitoringState() {
        return strategyState == StrategyState.ACTIVE || strategyState == StrategyState.PAUSED;
    }

    protected boolean isExpiredAt(LocalDateTime localDateTime) {
        boolean expireTimeConfigured = expireTime != null;
        if (expireTimeConfigured) {
            return localDateTime.compareTo(expireTime) >= 0;
        }
        return false;
    }

    @Override
    public void onTradeSignal(TriggerTradingContext triggerTradingContext) {
        List<EntrustCommand> entrustCommands = createEntrustCommands((TradeSignal) triggerTradingContext.getSignal(), triggerTradingContext);
        for (EntrustCommand entrustCommand : entrustCommands) {
            handleEntrustCommand(triggerTradingContext, entrustCommand);
        }
        afterEntrustCommandsExecuted(triggerTradingContext);
    }

    private void handleEntrustCommand(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand) {
        TradeCustomer tradeCustomer = triggerTradingContext.getTradeCustomer();
        try {
            EntrustResult entrustResult = tradeCustomer.entrust(entrustCommand);
            logger.info("Entrust result <== {}", entrustResult);
            afterEntrustSuccess(triggerTradingContext, entrustCommand, entrustResult);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public List<EntrustCommand> createEntrustCommands(TradeSignal tradeSignal, TradingMarketSupplier tradingMarketSupplier) {
        SingleEntrustTradePlan singleEntrustTradePlan = (SingleEntrustTradePlan) getTradePlan();
        EntrustCommand entrustCommand = singleEntrustTradePlan.createEntrustCommand(tradeSignal, getSecurityInfo(),
                tradingMarketSupplier);
        return Collections.singletonList(entrustCommand);
    }

    @Override
    public void afterEntrustSuccess(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand, EntrustResult entrustResult) {
        triggerTradingContext.saveEntrustOrder(new EntrustOrderInfo(orderId, tradeCustomerInfo, entrustCommand, entrustResult));
    }

    @Override
    public void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        setStrategyState(StrategyState.TERMINATED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractConditionOrder that = (AbstractConditionOrder) o;

        if (!orderId.equals(that.orderId)) {
            return false;
        }
        if (!tradeCustomerInfo.equals(that.tradeCustomerInfo)) {
            return false;
        }
        if (!securityInfo.equals(that.securityInfo)) {
            return false;
        }
        if (expireTime != null ? !expireTime.equals(that.expireTime) : that.expireTime != null) {
            return false;
        }
        return strategyState == that.strategyState;
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + tradeCustomerInfo.hashCode();
        result = 31 * result + securityInfo.hashCode();
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = 31 * result + strategyState.hashCode();
        return result;
    }
}
